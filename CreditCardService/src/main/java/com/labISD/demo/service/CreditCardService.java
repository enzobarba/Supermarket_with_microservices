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

    public boolean spendMoneyFromCard(PaymentDTO paymentDTO){
        //check first if card exists (different method called by cartService)
        boolean canSpend = false;
        CreditCard creditCard = creditCardRepository.findByNumber(paymentDTO.cardNumber());
        if(creditCard.canSpendMoney(paymentDTO.amount())){
            creditCard.spendMoney(paymentDTO.amount());
            canSpend = true;
            creditCardRepository.save(creditCard);
        }
        return canSpend;
    }

    public String getUserCards(UUID userId){
        List <CreditCard> creditCards = creditCardRepository.findByUserId(userId);
        if(creditCards.size() == 0){
            return "no credit cards";
        }
        return creditCards.toString();
    }

    private boolean isValidExpirationDate(String expirationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth expDate = YearMonth.parse(expirationDate, formatter);
        return expDate.isAfter(YearMonth.now()); 
    }
}
