package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.RegisterAccountDTO;

@RestController
public class ApiGatewayController {

    @Autowired
    ApiGatewayService apiGatewayService;
    
    @PostMapping("/account/registerAccount")
    public String registerAccount(RegisterAccountDTO registerAccountDTO){
        return apiGatewayService.registerAccount(registerAccountDTO);
    }

    
}
