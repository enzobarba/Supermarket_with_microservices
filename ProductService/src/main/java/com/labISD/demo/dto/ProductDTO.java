package com.labISD.demo.dto;
import lombok.Setter;
import lombok.Getter;

public class ProductDTO {

    @Getter @Setter
    private String name;
    @Getter @Setter
    private int quantity;
    @Getter @Setter
    private float price;

    public ProductDTO(String name, int quantity, float price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}
