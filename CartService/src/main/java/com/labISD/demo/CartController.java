package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/createCart")
    public void createCart(@RequestBody UUID userId){
        cartService.createCart(userId);
    }

    @GetMapping("/addItemToCart")
    public void addItemToCart(@RequestParam(value = "userId") UUID userId, @RequestParam(value = "productId") UUID productId, @RequestParam(value = "q") int q){
        cartService.addItemToCart(userId, productId, q);
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
