package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    
    @Autowired
    private AccountService accountService;

    @GetMapping("/registerAccount")
    public void registerAccount(){
        accountService.registerAccount("gino", "2709Gino*", "ginopollo@gmail.com", "Gino", "Pollo");
        accountService.registerAccount("gino2", "2709Gino*", "ginopollo2@gmail.com", "Gino", "Pollo");
    }

    @GetMapping("/getAllAccounts")
    public String getAllAccounts(){
        return accountService.getAllAccounts().toString();
    }

    @GetMapping ("/logIn")
    public String logIn(){
        return accountService.logIn("gino", "2709Gino*");
    }

    @GetMapping("/checkToken")
    public boolean checkToken(@RequestParam (value = "t") String token){
        return accountService.checkToken(token);
    }
}
