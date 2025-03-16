package com.labISD.demo.dto;

import java.util.UUID;

public record PaymentDTO (UUID cardId, float amount) {
}
   
