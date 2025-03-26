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
        
    public void addItemToCart(UUID userId, UUID productId, int quantity) {
        Purchase cart = getCart(userId);
        PurchaseItem cartItem = cart.getItems().stream()
        .filter(item -> item.getId().equals(productId))
        .findFirst()
        .orElse(null);
        int totalQuantity = quantity;
        if(cartItem != null){
            totalQuantity+= cartItem.getQuantity();
        }
        Product product = productService.getProductById(productId);
        if(product.quantityAvailable(totalQuantity)){
            cart.addItemToCart(product, quantity);
            purchaseRepository.save(cart);
        }
    }
    
    public void removeItemFromCart(UUID userId, UUID productId) {
        Purchase cart = purchaseRepository.findByUserIdAndStatus(userId, ORDERSTATUS.CART).getFirst();
        cart.removeItem(productId);
        purchaseRepository.save(cart);
    }
    
    public void clearCart(UUID userId) {
        Purchase cart = purchaseRepository.findByUserIdAndStatus(userId, ORDERSTATUS.CART).getFirst();
        cart.clear();
        purchaseRepository.save(cart);
    }

    //TO DO: auth for use a card
    public String checkout(UUID userId, UUID cardId){
        Purchase cart = purchaseRepository.findByUserIdAndStatus(userId, ORDERSTATUS.CART).getFirst();
        if(cart.isEmpty()){
            return "add Products first!";
        }
        boolean productsAvailable = checkProductsAvailability(cart);
        if(!productsAvailable){
            return "quantities not available";
        }
        boolean canSpend = pay(new PaymentDTO(cardId, cart.getTotalAmount()));
        if(!canSpend){
            return "not enough money on credit card";
        }
        cart.setStatus(ORDERSTATUS.ORDER);
        decreaseProductsQuantity(cart);
        clearCart(userId);
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
