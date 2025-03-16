package com.labISD.demo.dto;

import java.util.UUID;

public record OrderItemDTO (UUID productId, String name, int quantity, float unitPrice, float totalPrice) {
}
   
