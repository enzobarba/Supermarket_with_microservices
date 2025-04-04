package com.labISD.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.labISD.demo.domain.CreditCard;
import java.util.List;
import java.util.UUID;

public interface CreditCardRepository extends JpaRepository <CreditCard, UUID>{
    
    public List <CreditCard> findByUserId(UUID userId);
    public CreditCard findByNumber(String number);

}
