package com.labISD.demo.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.*;
import com.labISD.demo.service.CreditCardService;

@RestController
public class CreditCardController {
    
    @Autowired
    private CreditCardService creditCardService;

    @PostMapping("/addCardToAccount")
    public String addCardToAccount(@RequestParam(value = "userId") UUID userId, @RequestBody NewCreditCardDTO creditCardDTO){
        return creditCardService.addCardToAccount(userId, creditCardDTO);
    }

    @GetMapping("/getUserCards")
    public String getUserCards(@RequestParam(value = "userId") UUID userId){
        return creditCardService.getUserCards(userId);
    }
        
    @PostMapping("/spendMoneyFromCard")
    public String spendMoneyFromCard(@RequestBody PaymentDTO paymentDTO){
        return creditCardService.spendMoneyFromCard(paymentDTO);
    }

}
