package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.RegisterAccountDTO;
import com.labISD.demo.dto.LoginDTO;

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
    
}
