package com.labISD.demo;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {
    
    @Autowired
    private CreditCardRepository creditCardRepository;

    public void addCardToProfile(UUID profileId, String number, String type, String expirationDate, float money){
        CreditCard creditCard = new CreditCard(profileId, number, type, expirationDate, money);
        creditCardRepository.save(creditCard);
    }

    public void deleteCard(UUID cardId){
        creditCardRepository.deleteById(cardId);
    }    

    public void spendMoneyFromCard(UUID cardId, float amount){
        Optional <CreditCard> creditCard = creditCardRepository.findById(cardId);
        creditCard.ifPresent(c -> {
            if(c.canSpendMoney(amount)){
                c.spendMoney(amount);
                creditCardRepository.save(c);
            }
        });
    }


}
