package com.labISD.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.*;
import com.labISD.demo.service.AccountService;

@RestController
public class AccountController {
    
    @Autowired
    private AccountService accountService;

    @PostMapping("/registerAccount")
    public String registerAccount(@RequestBody NewAccountDTO registerAccountDTO){
        return accountService.registerAccount(registerAccountDTO);
    }

    @PostMapping ("/login")
    public String login(@RequestBody LoginDTO loginDTO){
        return accountService.login(loginDTO);
    }

    @GetMapping("/checkToken")
    public boolean checkToken(@RequestHeader("Authorization") String token){
        return accountService.checkToken(token);
    }

    @GetMapping("/getUserByToken")
    public String getUserByToken(@RequestHeader("Authorization") String token){
        return accountService.getUserByToken(token);
    }

    @GetMapping("/getUserIdByToken")
    public UUID getUserIdByToken(@RequestHeader("Authorization") String token){
        return accountService.getUserIdByToken(token);
    }

    @GetMapping("/getAllAccounts")
    public String getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PostMapping ("/logout")
    public String login(@RequestBody String token){
        return accountService.logout(token);
    }

    @PostMapping("/checkRequest")
    public boolean checkRequest(@RequestBody RequestDTO requestDTO){
        return accountService.checkRequest(requestDTO);
    }
}
