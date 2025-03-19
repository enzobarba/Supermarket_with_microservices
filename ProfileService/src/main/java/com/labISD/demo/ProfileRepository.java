package com.labISD.demo;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository <Profile, UUID> {
    
    public Profile findByEmail(String email);
}
