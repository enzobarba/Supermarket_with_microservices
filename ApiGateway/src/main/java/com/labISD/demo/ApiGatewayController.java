package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.NewAccountDTO;
import com.labISD.demo.dto.NewCartItemDTO;
import com.labISD.demo.dto.LoginDTO;
import com.labISD.demo.dto.NewProductDTO;
import com.labISD.demo.dto.RateProductDTO;
import com.labISD.demo.dto.SupplyProductDTO;
import com.labISD.demo.enums.CATEGORY;

@RestController
public class ApiGatewayController {

    @Autowired
    ApiGatewayService apiGatewayService;
    
    @PostMapping("/account/registerAccount")
    public String registerAccount(@RequestBody NewAccountDTO registerAccountDTO){
        return apiGatewayService.registerAccount(registerAccountDTO);
    }

    @PostMapping("/account/login")
    public String login(@RequestBody LoginDTO loginDTO){
        return apiGatewayService.login(loginDTO);
    }

    @PostMapping("/account/logout")
    public String logout(@RequestBody String token){
        return apiGatewayService.logout(token);
    }

    @PostMapping("/product/addProduct")
    public String addProduct(@RequestHeader("Authorization") String token, @RequestBody NewProductDTO productDTO){
        return apiGatewayService.addProduct(token, productDTO);
    }

    @GetMapping("/account/getAllAccounts")
    public String getAllAccounts(@RequestHeader("Authorization") String token){
        return apiGatewayService.getAllAccounts(token);
    }

    @GetMapping("/product/getAllProducts")
    public String getAllProducts(@RequestHeader("Authorization") String token){
        return apiGatewayService.getAllProducts(token);
    }

    @GetMapping("/product/getSortedProductsByRatingDesc")
    public String getSortedProductsByRatingDesc(@RequestHeader("Authorization") String token){
        return apiGatewayService.getSortedProductsByRatingDesc(token);
    }

    @GetMapping("/product/getProductsByCategory")
    public String getProductsByCategory(@RequestHeader("Authorization") String token, @RequestParam (value = "category") CATEGORY category){
        return apiGatewayService.getProductsByCategory(token, category);
    }

    @PutMapping("/product/supplyProduct")
    public String supplyProduct(@RequestHeader("Authorization") String token, @RequestBody SupplyProductDTO supplyProductDTO){
        return apiGatewayService.supplyProduct(token, supplyProductDTO);
    }

    @PutMapping("/product/rateProduct")
    public String rateProduct(@RequestHeader("Authorization") String token, @RequestBody RateProductDTO rateProductDTO){
        return apiGatewayService.rateProduct(token, rateProductDTO);
    }

    @GetMapping("/product/getCart")
    public String getCart(@RequestHeader("Authorization") String token){
        return apiGatewayService.getCart(token);
    }

    @GetMapping("/product/getOrders")
    public String getOrders(@RequestHeader("Authorization") String token){
        return apiGatewayService.getOrders(token);
    }

    @DeleteMapping("/product/clearCart")
    public String clearCart(@RequestHeader("Authorization") String token){
        return apiGatewayService.clearCart(token);
    }

    @PostMapping("/product/addItemToCart")
    public String addItemToCart(@RequestHeader("Authorization") String token, @RequestBody NewCartItemDTO newCartItemDTO){
        return apiGatewayService.addItemToCart(token, newCartItemDTO);
    }

    @DeleteMapping("/product/removeItemFromCart")
    public String removeItemFromCart(@RequestHeader("Authorization") String token, @RequestParam (value = "productName") String productName){
        return apiGatewayService.removeItemFromCart(token, productName);
    }

    @PostMapping("/product/checkout")
    public String checkout(@RequestHeader("Authorization") String token, @RequestBody String cardNumber){
        return apiGatewayService.checkout(token, cardNumber);
    }

}
