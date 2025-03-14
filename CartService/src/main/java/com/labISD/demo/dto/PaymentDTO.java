package com.labISD.demo.dto;

import lombok.Setter;
import lombok.Getter;
import java.util.UUID;

public class PaymentDTO {
    
    @Getter @Setter
    private UUID cardId;
    @Getter @Setter
    private float amount;

    public PaymentDTO(UUID cardId, float amount) {
        this.cardId = cardId;
        this.amount = amount;
    }
}
