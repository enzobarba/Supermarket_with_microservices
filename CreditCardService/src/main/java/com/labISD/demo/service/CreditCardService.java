package com.labISD.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.labISD.demo.domain.CreditCard;
import com.labISD.demo.dto.NewCreditCardDTO;
import com.labISD.demo.dto.PaymentDTO;
import com.labISD.demo.repository.CreditCardRepository;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class CreditCardService {
    
    @Autowired
    private CreditCardRepository creditCardRepository;

    public String addCardToAccount(UUID userId, NewCreditCardDTO newCreditCardDTO){
        if(creditCardRepository.findByNumber(newCreditCardDTO.number()) != null){
            return "Error: Credit card number already exists";
        }
        if(!isValidExpirationDate(newCreditCardDTO.expirationDate())){
            return "Error: Expiration date is invalid or in the past";
        }
        CreditCard creditCard = new CreditCard(userId,newCreditCardDTO.number(), newCreditCardDTO.type(), newCreditCardDTO.expirationDate(), newCreditCardDTO.money());
        creditCardRepository.save(creditCard);
        return "Credit card successfully added";
    }

    public boolean cardExists(String number){
        CreditCard creditCard = creditCardRepository.findByNumber(number);
        if(creditCard == null){
            return false;
        }
        return true;
    }

    public String spendMoneyFromCard(PaymentDTO paymentDTO){
        CreditCard creditCard = creditCardRepository.findByNumberAndUserId(paymentDTO.cardNumber(), paymentDTO.userId());
        if(creditCard == null){
            return "ERROR: credit card not found";
        }
        else if(!creditCard.canSpendMoney(paymentDTO.amount())){
            return "ERROR: not enough money on card";
        }
        creditCard.spendMoney(paymentDTO.amount());
        creditCardRepository.save(creditCard);
        return "OK";
    }

    public String getUserCards(UUID userId){
        List <CreditCard> creditCards = creditCardRepository.findByUserId(userId);
        if(creditCards.size() == 0){
            return "No credit cards";
        }
        String printCards = "User cards:";
        for(int i = 0; i < creditCards.size(); i++){
            printCards+= String.format("\n%d) %s",(i+1),creditCards.get(i).toString());;
        }
        return printCards;
    }

    private boolean isValidExpirationDate(String expirationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth expDate = YearMonth.parse(expirationDate, formatter);
        return expDate.isAfter(YearMonth.now()); 
    }
}
