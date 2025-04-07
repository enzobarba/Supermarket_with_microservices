package com.labISD.demo.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.labISD.demo.domain.Account;
import com.labISD.demo.enums.ROLE;
import java.util.List;


public interface AccountRepository extends JpaRepository <Account, UUID> {
    
    public Account findByUsername(String username);
    public List <Account> findByRole(ROLE role);
    public Account findByEmail(String email);

}
