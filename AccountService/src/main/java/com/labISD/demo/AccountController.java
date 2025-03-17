package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.RegisterAccountDTO;

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
        return accountService.getAllAccounts().toString();
    }

    @GetMapping ("/logIn")
    public String logIn(){
        return accountService.logIn("gino", "2709Gino*");
    }

    //TO DO: ADD LOGOUT (DESTROY TOKEN)

    @GetMapping("/checkToken")
    public boolean checkToken(@RequestParam (value = "t") String token){
        return accountService.checkToken(token);
    }
}
