package com.labISD.demo.dto;

import java.util.List;
import java.util.UUID;

public record OrderDTO(UUID userId, List<OrderItemDTO> items, float totalAmount) {
}
    