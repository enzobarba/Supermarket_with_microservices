package com.labISD.demo.dto;

import java.util.UUID;

public record NewCreditCardDTO(UUID userId, String number, String type, String expirationDate, float money) {
}
