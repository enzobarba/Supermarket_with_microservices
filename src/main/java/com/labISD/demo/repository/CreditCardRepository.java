package com.labISD.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.labISD.demo.domain.CreditCard;

public interface CreditCardRepository extends JpaRepository <CreditCard, UUID>{
    
    public CreditCard findByProfileId(UUID profileId);

}
