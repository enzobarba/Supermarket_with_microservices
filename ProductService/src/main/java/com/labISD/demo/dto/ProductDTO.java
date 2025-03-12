package com.labISD.demo.dto;
import lombok.Setter;
import lombok.Getter;

public class ProductDTO {

    @Getter @Setter
    private String name;
    @Getter @Setter
    private int quantity;

    public ProductDTO(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
