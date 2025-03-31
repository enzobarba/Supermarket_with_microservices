package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.NewAccountDTO;
import com.labISD.demo.dto.LoginDTO;
import com.labISD.demo.dto.NewProductDTO;

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

}
