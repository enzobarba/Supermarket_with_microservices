package com.labISD.demo.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.labISD.demo.domain.Account;


public interface AccountRepository extends JpaRepository <Account, UUID> {
    
}
