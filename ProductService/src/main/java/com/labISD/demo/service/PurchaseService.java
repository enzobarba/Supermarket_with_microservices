package com.labISD.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.domain.*;
import com.labISD.demo.dto.*;
import com.labISD.demo.enums.ORDERSTATUS;
import com.labISD.demo.repository.PurchaseRepository;
import java.util.UUID;

@Service
public class PurchaseService {
    
    private PurchaseRepository purchaseRepository;
    private WebClient.Builder webClientBuilder;
    private ProductService productService;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, WebClient.Builder webClientBuilder, ProductService productService){
        this.purchaseRepository = purchaseRepository;
        this.webClientBuilder = webClientBuilder;
        this.productService = productService;
    }
    
    public Purchase getCart(UUID userId) {
        return purchaseRepository.findByUserIdAndStatus(userId, ORDERSTATUS.CART).getFirst();
    }

    public String getOrders(UUID userId){
        return purchaseRepository.findByUserIdAndStatus(userId, ORDERSTATUS.ORDER).toString();
    }
    
    public void createCart(UUID userId) {
        Purchase purchase = new Purchase(userId);
        purchaseRepository.save(purchase);
    }
        
    public String addItemToCart(UUID userId, NewCartItemDTO newCartItemDTO) {
        Purchase cart = getCart(userId);
        UUID productId = productService.getProductIdByName(newCartItemDTO.name());
        if(productId == null){
            return "ERROR adding product to cart: product not found";
        }
        PurchaseItem cartItem = cart.getItems().stream()
        .filter(item -> item.getId().equals(productId))
        .findFirst()
        .orElse(null);
        int totalQuantity = newCartItemDTO.quantity();
        if(cartItem != null){
            totalQuantity+= cartItem.getQuantity();
        }
        Product product = productService.getProductById(productId);
        if(product.quantityAvailable(totalQuantity)){
            cart.addItemToCart(product, newCartItemDTO.quantity());
            purchaseRepository.save(cart);
            return "product successfully added to cart";
        }
        return "ERROR adding product to cart: quantity non available";
    }
    
    public String removeItemFromCart(UUID userId, String productName) {
        UUID productId = productService.getProductIdByName(productName);
        Purchase cart = purchaseRepository.findByUserIdAndStatus(userId, ORDERSTATUS.CART).getFirst();
        boolean productFoundInCart = cart.removeItem(productId);
        if(productFoundInCart){
            purchaseRepository.save(cart);
            return "product successfully deleted form cart";
        }
        return "ERROR deleting product from cart: product not found";
    }
    
    public String clearCart(UUID userId) {
        Purchase cart = purchaseRepository.findByUserIdAndStatus(userId, ORDERSTATUS.CART).getFirst();
        cart.clear();
        purchaseRepository.save(cart);
        return "Cart successfully cleared";
    }

    public String checkout(UUID userId, String cardNumber){
        Purchase cart = purchaseRepository.findByUserIdAndStatus(userId, ORDERSTATUS.CART).getFirst();
        if(cart.isEmpty()){
            return "ERROR: cart is empty!";
        }
        boolean productsAvailable = checkProductsAvailability(cart);
        if(!productsAvailable){
            return "ERROR: quantities not available";
        }
        boolean canSpend = pay(new PaymentDTO(cardNumber, cart.getTotalAmount()));
        if(!canSpend){
            return "ERROR: not enough money on credit card";
        }
        decreaseProductsQuantity(cart);
        cart.setStatus(ORDERSTATUS.ORDER);
        purchaseRepository.save(cart);
        createCart(userId);
        return "purchase made successfully!";
    }

    private boolean checkProductsAvailability(Purchase cart){
        boolean notAllAvailable = cart.getItems().stream()
        .anyMatch(item -> item.getProduct().quantityAvailable(item.getQuantity()) == false);
        return !notAllAvailable;
    }

    private void decreaseProductsQuantity(Purchase cart){
        cart.getItems().forEach(item -> item.getProduct().buy(item.getQuantity()));
        cart.getItems().forEach(item -> productService.saveProduct(item.getProduct()));
    }

    private boolean pay(PaymentDTO paymentDTO) {
        return webClientBuilder.build()
            .post().uri("http://creditCard:9092/spendMoneyFromCard")
            .bodyValue(paymentDTO)
            .retrieve().bodyToMono(Boolean.class).block(); 
    }

}
