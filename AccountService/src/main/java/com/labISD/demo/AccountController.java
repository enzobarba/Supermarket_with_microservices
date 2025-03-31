package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.*;

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

    @PostMapping("/checkToken")
    public boolean checkToken(@RequestBody String token){
        return accountService.checkToken(token);
    }

    @PostMapping("/getUserByToken")
    public String getUserByToken(@RequestBody String token){
        return accountService.getUserByToken(token);
    }

    @GetMapping("/getAllAccounts")
    public String getAllAccounts(){
        return accountService.getAllAccounts();
    }
    //TO DO: ADD LOGOUT (DESTROY TOKEN)

    
    @PostMapping("/checkRequest")
    public boolean checkRequest(@RequestBody RequestDTO requestDTO){
        return accountService.checkRequest(requestDTO);
    }
}
