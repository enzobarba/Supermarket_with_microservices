package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    public Cart getCart(UUID userId) {
        return cartRepository.findByUserId(userId);
    }
    
    public void createCart(UUID userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cartRepository.save(cart);
    }
        
    public void addItemToCart(UUID userId, UUID productId, int quantity) {
        Cart cart = getCart(userId);
        CartItem cartItem = cart.getItems().stream()
        .filter(item -> item.getProductId().equals(productId))
        .findFirst()
        .orElse(null);
        int totalQuantity = quantity;
        if(cartItem != null){
            totalQuantity+= cartItem.getQuantity();
        }
        ProductDTO productDTO = getProductDTO(productId);
        if(productDTO.quantity() >= totalQuantity){
            cart.addItemToCart(productId, productDTO.name(), quantity, productDTO.price());
            cartRepository.save(cart);
        }
    }

    private ProductDTO getProductDTO(UUID productId) {
        return webClientBuilder.build()
            .post().uri("http://product:9092/getProductDTO")
            .bodyValue(productId)
            .retrieve().bodyToMono(ProductDTO.class).block(); 
    }
    
    public void removeItemFromCart(UUID userId, UUID productId) {
        Cart cart = cartRepository.findByUserId(userId);
        cart.removeItem(productId);
        cartRepository.save(cart);
    }
    
    public void clearCart(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId);
        cart.clear();
        cartRepository.save(cart);
    }

    //TO DO: auth for use a card
    public String checkout(UUID userId, UUID cardId){
        Cart cart = cartRepository.findByUserId(userId);
        if(cart.isEmpty()){
            return "add Products first!";
        }
        List <ProductAvailableDTO> productsList = createProductAvailableDTOs(cart);
        boolean productsAvailable = sendProductAvailableDTO(productsList);
        if(!productsAvailable){
            return "quantities not available";
        }
        //nel carrello ci potrebbero essere prezzi diversi da quelli dell'inventario (dovrei mettere sincronizzazione prodotto <-> carrelli tutti utenti)
        boolean canSpend = checkPayment(new PaymentDTO(cardId, cart.getTotalAmount()));
        if(!canSpend){
            return "not enough money on credit card";
        }
        OrderDTO orderDTO = createOrderDTO(cart, userId);
        sendOrderDTO(orderDTO);
        decreaseProductsQuantity(productsList);
        clearCart(userId);
        return "purchase made successfully!";
    }

    private List <ProductAvailableDTO> createProductAvailableDTOs(Cart cart){
        List <ProductAvailableDTO> productsList = new ArrayList<>();
        cart.getItems().forEach(item -> {
            productsList.add(new ProductAvailableDTO(item.getProductId(), item.getQuantity()));
        });
        return productsList;
    }

    private boolean sendProductAvailableDTO(List <ProductAvailableDTO> productsList) {
        return webClientBuilder.build()
            .post().uri("http://product:9092/productsAvailable")
            .bodyValue(productsList)
            .retrieve().bodyToMono(Boolean.class).block(); 
    }

    private void decreaseProductsQuantity(List <ProductAvailableDTO> productsList) {
        webClientBuilder.build()
            .post().uri("http://product:9092/decreaseProductsQuantity")
            .bodyValue(productsList)
            .retrieve().toBodilessEntity().block(); 
    }

    private boolean checkPayment(PaymentDTO paymentDTO) {
        return webClientBuilder.build()
            .post().uri("http://creditCard:9093/spendMoneyFromCard")
            .bodyValue(paymentDTO)
            .retrieve().bodyToMono(Boolean.class).block(); 
    }

    private OrderDTO createOrderDTO(Cart cart, UUID userId){
        List <OrderItemDTO> orderItemDTOs = new ArrayList<>();
        cart.getItems().forEach( item -> {
            orderItemDTOs.add(new OrderItemDTO(item.getProductId(), item.getName(), item.getQuantity(), item.getUnitPrice(), item.getUnitPrice()*item.getQuantity()));
        });
        OrderDTO orderDTO = new OrderDTO(userId, orderItemDTOs, cart.getTotalAmount());
        return orderDTO;
    }

    private void sendOrderDTO(OrderDTO orderDTO) {
        webClientBuilder.build()
            .post().uri("http://order:9095/createOrder")
            .bodyValue(orderDTO)
            .retrieve().toBodilessEntity().block(); 
    }
    
}
