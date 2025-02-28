package com.labISD.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.labISD.demo.domain.Profile;


public interface ProfileRepository extends JpaRepository <Profile, UUID> {
    
}
