package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.labISD.demo.dto.CreditCardDTO;
import com.labISD.demo.dto.PaymentDTO;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CreditCardService {
    
    @Autowired
    private CreditCardRepository creditCardRepository;

    public String addCardToAccount(CreditCardDTO creditCardDTO){
        if(creditCardRepository.findByNumber(creditCardDTO.number()) != null){
            return "Error: Credit card number already exists";
        }
        if(!isValidExpirationDate(creditCardDTO.expirationDate())){
            return "Error: Expiration date is invalid or in the past";
        }
        CreditCard creditCard = new CreditCard(creditCardDTO.userId(),creditCardDTO.number(), creditCardDTO.type(), creditCardDTO.expirationDate(), creditCardDTO.money());
        creditCardRepository.save(creditCard);
        return "Credit card successfully added";
    }

    private boolean isValidExpirationDate(String expirationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth expDate = YearMonth.parse(expirationDate, formatter);
        return expDate.isAfter(YearMonth.now()); 
    }

    public boolean spendMoneyFromCard(PaymentDTO paymentDTO){
        //check first if card exists (different method called by cartService)
        boolean canSpend = false;
        CreditCard creditCard = creditCardRepository.findById(paymentDTO.cardId()).get();
        if(creditCard.canSpendMoney(paymentDTO.amount())){
            creditCard.spendMoney(paymentDTO.amount());
            canSpend = true;
            creditCardRepository.save(creditCard);
        }
        return canSpend;
    }

    public String getAllCards(){
        List <CreditCard> creditCards = creditCardRepository.findAll();
        if(creditCards.size() == 0){
            return "no credit cards";
        }
        return creditCards.toString();
    }
}
