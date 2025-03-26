package com.labISD.demo.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.service.PurchaseService;

@RestController
public class PurchaseController {
    
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/getCart")
    public String getCart(@RequestParam(value = "userId") UUID userId){   
        return purchaseService.getCart(userId).toString();
    }

    @GetMapping("/getOrders")
    public String getOrders(@RequestParam(value = "userId") UUID userId){   
        return purchaseService.getOrders(userId);
    }

    @GetMapping("/createCart")
    public void createCart(@RequestParam(value = "userId") UUID userId){
        purchaseService.createCart(userId);
    }

    @GetMapping("/addItemToCart")
    public void addItemToCart(@RequestParam(value = "userId") UUID userId, @RequestParam(value = "productId") UUID productId, @RequestParam(value = "q") int q){
        purchaseService.addItemToCart(userId, productId, q);
    }

    @GetMapping("/removeItemFromCart")
    public void removeItemFromCart(@RequestParam(value = "userId") UUID userId, @RequestParam(value = "productId") UUID productId){
        purchaseService.removeItemFromCart(userId, productId);
    }

    @GetMapping("/clearCart")
    public void clearCart(@RequestParam(value = "userId") UUID userId){
        purchaseService.clearCart(userId);
    }  
    
    @GetMapping("/checkout")
    public String checkout(@RequestParam(value = "userId") UUID userId, @RequestParam(value = "cardId") UUID cardId){
        return purchaseService.checkout(userId, cardId);
    }
}
