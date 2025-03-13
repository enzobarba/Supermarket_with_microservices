package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.ProductDTO;
import com.labISD.demo.dto.ProductAvailableDTO;
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
        if(productDTO.getQuantity() >= totalQuantity){
            cart.addItemToCart(productId, productDTO.getName(), quantity, productDTO.getPrice());
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

    public String checkout(UUID userId){
        Cart cart = cartRepository.findByUserId(userId);
        List <ProductAvailableDTO> productsList = createProductAvailableDTOs(cart);
        boolean productsAvailable = sendProductAvailableDTO(productsList);
        if(! productsAvailable){
            return "Quantità non disponibili";
        }
        //Messaggio differente se sia productsAvailable e soldiDisponibili falso
        clearCart(userId);
        cart.setTotalAmount(0);
        cartRepository.save(cart);
        decreaseProductsQuantity(productsList);
        return "Acquisto effettuato con successo!";
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
    
}
