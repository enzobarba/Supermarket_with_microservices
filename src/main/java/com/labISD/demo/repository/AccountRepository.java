package com.labISD.demo.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.UUID;
import com.labISD.demo.domain.Account;


public interface AccountRepository extends CrudRepository <Account, UUID> {
    
}
