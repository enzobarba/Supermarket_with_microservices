package com.labISD.demo.dto;

import java.util.UUID;

public record PaymentDTO (UUID userId, String cardNumber, float amount) {
}
   
