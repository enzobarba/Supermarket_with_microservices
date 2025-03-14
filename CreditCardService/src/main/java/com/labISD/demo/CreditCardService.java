package com.labISD.demo;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CreditCardService {
    
    @Autowired
    private CreditCardRepository creditCardRepository;

    public void addCardToAccount(UUID accountId, String number, String type, String expirationDate, float money){
        CreditCard creditCard = new CreditCard(accountId, number, type, expirationDate, money);
        creditCardRepository.save(creditCard);
    }

    public void deleteCard(UUID cardId){
        creditCardRepository.deleteById(cardId);
    }    

    public boolean spendMoneyFromCard(UUID cardId, float amount){
        boolean canSpend = false;
        CreditCard creditCard = creditCardRepository.findById(cardId).get();
        if(creditCard.canSpendMoney(amount)){
            creditCard.spendMoney(amount);
            canSpend = true;
            creditCardRepository.save(creditCard);
        }
        return canSpend;
    }

    public List <CreditCard> getAllCards(){
        return creditCardRepository.findAll();
    }
}
