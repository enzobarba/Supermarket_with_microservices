package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.RegisterAccountDTO;
import com.labISD.demo.dto.RequestDTO;
import com.labISD.demo.dto.LoginDTO;
import com.labISD.demo.dto.ProductDTO;

@RestController
public class ApiGatewayController {

    @Autowired
    ApiGatewayService apiGatewayService;
    
    @PostMapping("/account/registerAccount")
    public String registerAccount(@RequestBody RegisterAccountDTO registerAccountDTO){
        return apiGatewayService.registerAccount(registerAccountDTO);
    }

    @GetMapping("/account/getAllAccounts")
    public String getAllAccounts(){
        return apiGatewayService.getAllAccounts();
    }

    @PostMapping("/account/login")
    public String login(@RequestBody LoginDTO loginDTO){
        return apiGatewayService.login(loginDTO);
    }

    @PostMapping("/account/checkRequest")
    public String checkRequest(@RequestBody RequestDTO requestDTO){
        return apiGatewayService.checkRequest(requestDTO);
    }


    @PostMapping("/product/addProduct")
    public String addProduct(@RequestBody ProductDTO productDTO){
        return apiGatewayService.addProduct(productDTO);
    }

    @GetMapping("/product/getAllProducts")
    public String getAllProducts(){
        return apiGatewayService.getAllProducts();
    }
    
}
