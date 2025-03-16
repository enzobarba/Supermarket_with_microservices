package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.PaymentDTO;
import java.util.UUID;

@RestController
public class CreditCardController {
    
    @Autowired
    private CreditCardService creditCardService;

    @GetMapping("/addCardToAccount")
    public void addCardToAccount(@RequestParam(value = "accountId") UUID accountId, @RequestParam(value = "number", defaultValue = "0000000000000000") String number, @RequestParam(value = "type", defaultValue = "Visa") String type, @RequestParam(value = "expDate", defaultValue = "01/33") String expirationDate, @RequestParam(value = "money") float money){
        creditCardService.addCardToAccount(accountId, number, type, expirationDate, money);
    }

    @GetMapping("/deleteCard")
    public void deleteCard(@RequestParam(value = "cardId") UUID cardId){
        creditCardService.deleteCard(cardId);
    }

    @GetMapping("/getAllCards")
    public String getAllCards(){
        return creditCardService.getAllCards().toString();
    }
        
    @PostMapping("/spendMoneyFromCard")
    public boolean spendMoneyFromCard(@RequestBody PaymentDTO paymentDTO){
        return creditCardService.spendMoneyFromCard(paymentDTO.cardId(), paymentDTO.amount());
    }
}
