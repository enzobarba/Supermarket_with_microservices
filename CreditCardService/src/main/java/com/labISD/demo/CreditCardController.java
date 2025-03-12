package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
public class CreditCardController {
    
    @Autowired
    private CreditCardService creditCardService;

    @GetMapping("/addCardToProfile")
    public void addCardToProfile(@RequestParam(value = "profileId") UUID profileId, @RequestParam(value = "number") String number, @RequestParam(value = "type") String type, @RequestParam(value = "expDate") String expirationDate, @RequestParam(value = "money") float money){
        creditCardService.addCardToProfile(profileId, number, type, expirationDate, money);
    }

    @GetMapping("/deleteCard")
    public void deleteCard(@RequestParam(value = "cardId") UUID cardId){
        creditCardService.deleteCard(cardId);
    }
        
    @GetMapping("/spendMoneyFromCard")
    public void spendMoneyFromCard(@RequestParam(value = "cardId") UUID cardId, @RequestParam(value = "amount") float amount){
        creditCardService.spendMoneyFromCard(cardId, amount);
    }
}
