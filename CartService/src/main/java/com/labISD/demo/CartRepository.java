package com.labISD.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    
    public Cart findByUserId(UUID userId);
}
