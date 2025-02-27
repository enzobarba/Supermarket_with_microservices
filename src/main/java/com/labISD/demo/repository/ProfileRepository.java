package com.labISD.demo.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.UUID;
import com.labISD.demo.domain.Profile;


public interface ProfileRepository extends CrudRepository <Profile, UUID> {
    
}
