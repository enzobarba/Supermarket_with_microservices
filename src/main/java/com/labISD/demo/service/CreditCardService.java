package com.labISD.demo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import com.labISD.demo.domain.CreditCard;
import com.labISD.demo.repository.CreditCardRepository;

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

    public boolean cardHasEnoughMoney(UUID cardId, float amount){
        Optional <CreditCard> creditCard = creditCardRepository.findById(cardId);
        return creditCard.get().canSpendMoney(amount);
    }    

    public void spendMoneyFromCard(UUID cardId, float amount){
        Optional <CreditCard> creditCard = creditCardRepository.findById(cardId);
        creditCard.ifPresent(c -> {
            c.spendMoney(amount);
            creditCardRepository.save(c);
        });
    }


}
