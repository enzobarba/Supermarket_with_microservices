package com.labISD.demo.dto;

import lombok.Setter;
import lombok.Getter;
import java.util.UUID;

public class ProductAvailableDTO {

    @Getter @Setter
    private UUID productId;
    @Getter @Setter
    private int quantity;

    public ProductAvailableDTO(UUID productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}