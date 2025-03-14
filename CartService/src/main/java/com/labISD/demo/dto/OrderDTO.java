package com.labISD.demo.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class OrderDTO {

    private UUID userId;
    private List<OrderItemDTO> items;
    private float totalAmount;

    public OrderDTO(UUID userId, List<OrderItemDTO> items, float totalAmount) {
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
    }
}
