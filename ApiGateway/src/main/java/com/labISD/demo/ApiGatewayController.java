package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/product/addProduct")
    public String addProduct(@RequestBody NewProductDTO productDTO){
        return apiGatewayService.addProduct(productDTO);
    }

    @PostMapping("/account/getAllAccounts")
    public String getAllAccounts(@RequestBody String token){
        return apiGatewayService.getAllAccounts(token);
    }

    @PostMapping("/product/getAllProducts")
    public String getAllProducts(@RequestBody String token){
        return apiGatewayService.getAllProducts(token);
    }

}
