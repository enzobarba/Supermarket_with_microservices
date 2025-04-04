package com.labISD.demo.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.NewCartItemDTO;
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

    @PostMapping("/addItemToCart")
    public String addItemToCart(@RequestParam(value = "userId") UUID userId, @RequestBody NewCartItemDTO newCartItemDTO){
        return purchaseService.addItemToCart(userId, newCartItemDTO);
    }

    @DeleteMapping("/removeItemFromCart")
    public String removeItemFromCart(@RequestParam(value = "userId") UUID userId, @RequestParam(value = "productName") String productName){
        return purchaseService.removeItemFromCart(userId, productName);
    }

    @DeleteMapping("/clearCart")
    public String clearCart(@RequestParam (value = "userId") UUID userId){
        return purchaseService.clearCart(userId);
    }  
    
    @PostMapping("/checkout")
    public String checkout(@RequestParam(value = "userId") UUID userId, @RequestBody String cardNumber){
        return purchaseService.checkout(userId, cardNumber);
    }
}
