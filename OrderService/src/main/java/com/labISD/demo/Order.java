package com.labISD.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.GenerationType;
import java.util.UUID;
import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.CascadeType;

@Entity
public class Order {
    
    @Id @GeneratedValue(strategy = GenerationType.UUID) @Getter
    private UUID orderId;

    @NotNull(message = "user id cannot be null") @Getter @Setter
    private UUID userId;

   /* @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) 
    @Getter 
    private List <OrderItem> items;*/ 

    @Getter @Setter
    @Min(value = 0, message = "amount must be greater than or equal to 0")
    private float amount;

    protected Order(){}


}
