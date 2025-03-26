package com.labISD.demo.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.labISD.demo.enums.ORDERSTATUS;
import com.labISD.demo.domain.Purchase;

public interface PurchaseRepository extends JpaRepository <Purchase, UUID>{
    
    public List<Purchase> findByUserIdAndStatus(UUID userId, ORDERSTATUS status);
    
}
