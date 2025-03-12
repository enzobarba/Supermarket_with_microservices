package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
public class CartController {
    
    @Autowired
    private CartService cartService;

    @GetMapping("/getCart")
    public String getCart(@RequestParam(value = "userId") UUID userId){   
        return cartService.getCart(userId).toString();
    }

    //TO DO: DELETE AFTER INSERTING API CALL IN ACCOUNTSERVICE  
    @GetMapping("/createCart")
    public void createCart(@RequestParam(value = "userId") UUID userId){
        cartService.createCart(userId);
    }

    @GetMapping("/addItemToCart")
    public void addItemToCart(@RequestParam(value = "userId") UUID userId, @RequestParam(value = "productId") UUID productId, @RequestParam(value = "quantity") int quantity){
        cartService.addItemToCart(userId, productId, quantity);
    }

    @GetMapping("/removeItemFromCart")
    public void removeItemFromCart(@RequestParam(value = "userId") UUID userId, @RequestParam(value = "productId") UUID productId){
        cartService.removeItemFromCart(userId, productId);
    }

    @GetMapping("/clearCart")
    public void clearCart(@RequestParam(value = "userId") UUID userId){
        cartService.clearCart(userId);
    }   

}
