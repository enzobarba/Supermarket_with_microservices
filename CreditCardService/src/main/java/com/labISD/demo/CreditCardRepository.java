package com.labISD.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CreditCardRepository extends JpaRepository <CreditCard, UUID>{
    
    public CreditCard findByUserId(UUID userId);
    public CreditCard findByNumber(String number);

}
