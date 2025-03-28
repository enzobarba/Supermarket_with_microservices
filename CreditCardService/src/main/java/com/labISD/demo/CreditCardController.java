package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.*;

@RestController
public class CreditCardController {
    
    @Autowired
    private CreditCardService creditCardService;

    @PostMapping("/addCardToAccount")
    public String addCardToAccount(@RequestBody CreditCardDTO creditCardDTO){
        return creditCardService.addCardToAccount(creditCardDTO);
    }

    @GetMapping("/getAllCards")
    public String getAllCards(){
        return creditCardService.getAllCards();
    }
        
    @PostMapping("/spendMoneyFromCard")
    public boolean spendMoneyFromCard(@RequestBody PaymentDTO paymentDTO){
        return creditCardService.spendMoneyFromCard(paymentDTO);
    }
}
