package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;  

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    public Cart getCart(UUID userId) {
        return cartRepository.findByUserId(userId);
    }
    
    //TO DO: INVOKED BY ACCOUNTSERVICE DURING SIGN UP
    public void createCart(UUID userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cartRepository.save(cart);
    }
    
    //TODO: CHECK QUANTITY AVIALABLE INVOKING PRODUCTSERVICE
    
    public void addItemToCart(UUID userId, UUID productId, int quantity) {
        Cart cart = getCart(userId);
        cart.addItemToCart(productId, quantity);
        cartRepository.save(cart);
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
