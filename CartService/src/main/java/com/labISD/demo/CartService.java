package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.labISD.demo.dto.ProductDTO;
import java.util.UUID;  

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
        ProductDTO productDTO = getNameQuantityProduct(productId);
        if(productDTO.getQuantity() >= totalQuantity){
            cart.addItemToCart(productId, productDTO.getName(), quantity);
            cartRepository.save(cart);
        }
    }

    private ProductDTO getNameQuantityProduct(UUID productId) {
        return webClientBuilder.build()
            .post().uri("http://product:9092/getNameQuantityProduct")
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
    
}
