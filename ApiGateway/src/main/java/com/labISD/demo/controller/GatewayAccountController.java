package com.labISD.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.NewAccountDTO;
import com.labISD.demo.service.GatewayAccountService;
import com.labISD.demo.dto.LoginDTO;


@RestController
@RequestMapping("/account")
public class GatewayAccountController {

    @Autowired
    GatewayAccountService gatewayAccountService;

    @PostMapping("/registerAccount")
    public String registerAccount(@RequestBody NewAccountDTO registerAccountDTO){
        return gatewayAccountService.registerAccount(registerAccountDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO){
        return gatewayAccountService.login(loginDTO);
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String token){
        return gatewayAccountService.logout(token);
    }

    @GetMapping("/getAllAccounts")
    public String getAllAccounts(@RequestHeader("Authorization") String token){
        return gatewayAccountService.getAllAccounts(token);
    }
    }

