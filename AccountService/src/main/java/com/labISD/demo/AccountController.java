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
    public String registerAccount(@RequestBody RegisterAccountDTO registerAccountDTO){
        return accountService.registerAccount(registerAccountDTO);
    }

    @GetMapping("/getAllAccounts")
    public String getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PostMapping ("/login")
    public String login(@RequestBody LoginDTO loginDTO){
        return accountService.login(loginDTO);
    }

    //TO DO: ADD LOGOUT (DESTROY TOKEN)

    @PostMapping("/checkToken")
    public boolean checkToken(@RequestBody String token){
        return accountService.checkToken(token);
    }
}
