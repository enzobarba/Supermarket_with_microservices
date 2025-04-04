package com.labISD.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.NewProductDTO;
import com.labISD.demo.dto.SupplyProductDTO;
import com.labISD.demo.dto.NewCartItemDTO;
import com.labISD.demo.dto.RateProductDTO;
import com.labISD.demo.enums.CATEGORY;
import com.labISD.demo.service.GatewayProductService;


@RestController
@RequestMapping("/product")
public class GatewayProductController {

    @Autowired
    GatewayProductService gatewayProductService;

    @PostMapping("/addProduct")
    public String addProduct(@RequestHeader("Authorization") String token, @RequestBody NewProductDTO productDTO){
        return gatewayProductService.addProduct(token, productDTO);
    }

    @GetMapping("/getAllProducts")
    public String getAllProducts(@RequestHeader("Authorization") String token){
        return gatewayProductService.getAllProducts(token);
    }

    @GetMapping("/getSortedProductsByRatingDesc")
    public String getSortedProductsByRatingDesc(@RequestHeader("Authorization") String token){
        return gatewayProductService.getSortedProductsByRatingDesc(token);
    }

    @GetMapping("/getProductsByCategory")
    public String getProductsByCategory(@RequestHeader("Authorization") String token, @RequestParam (value = "category") CATEGORY category){
        return gatewayProductService.getProductsByCategory(token, category);
    }

    @PutMapping("/supplyProduct")
    public String supplyProduct(@RequestHeader("Authorization") String token, @RequestBody SupplyProductDTO supplyProductDTO){
        return gatewayProductService.supplyProduct(token, supplyProductDTO);
    }

    @PutMapping("/rateProduct")
    public String rateProduct(@RequestHeader("Authorization") String token, @RequestBody RateProductDTO rateProductDTO){
        return gatewayProductService.rateProduct(token, rateProductDTO);
    }

    @GetMapping("/getCart")
    public String getCart(@RequestHeader("Authorization") String token){
        return gatewayProductService.getCart(token);
    }

    @GetMapping("/getOrders")
    public String getOrders(@RequestHeader("Authorization") String token){
        return gatewayProductService.getOrders(token);
    }

    @DeleteMapping("/clearCart")
    public String clearCart(@RequestHeader("Authorization") String token){
        return gatewayProductService.clearCart(token);
    }

    @PostMapping("/addItemToCart")
    public String addItemToCart(@RequestHeader("Authorization") String token, @RequestBody NewCartItemDTO newCartItemDTO){
        return gatewayProductService.addItemToCart(token, newCartItemDTO);
    }

    @DeleteMapping("/removeItemFromCart")
    public String removeItemFromCart(@RequestHeader("Authorization") String token, @RequestParam (value = "productName") String productName){
        return gatewayProductService.removeItemFromCart(token, productName);
    }

    @PostMapping("/checkout")
    public String checkout(@RequestHeader("Authorization") String token, @RequestBody String cardNumber){
        return gatewayProductService.checkout(token, cardNumber);
    }
}
