package com.labISD.demo.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter @Setter
public class OrderItemDTO {
    private UUID productId;
    private String name;
    private int quantity;
    private float unitPrice;  
    private float totalPrice;

    public OrderItemDTO(UUID productId, String name, int quantity, float unitPrice) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = quantity * unitPrice;
    }
}
