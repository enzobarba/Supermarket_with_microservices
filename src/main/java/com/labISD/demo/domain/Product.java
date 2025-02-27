package com.labISD.demo.domain;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Product {
    
    @Id @GeneratedValue(strategy = GenerationType.UUID) @Getter
    private UUID id;

    @NotNull @Getter @Setter
    private String name;

    @NotNull @Getter @Setter
    private float price;

    @NotNull @Getter @Setter
    private int quantity;

    @NotNull @Getter @Setter
    private boolean avialable;

    @NotNull @Getter @Setter
    private float weight;

    @NotNull @Getter @Setter @Min(0) @Max(5)
    private float rating;

    protected Product(){}

    public Product(String name, float price, int quantity, float weight, float rating){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
        this.rating = rating;
    }

    @Override
    public String toString(){
        return "Id: " + id + ", Name: " + name + ", Price: " +price + ", Quantity: " + quantity + ", Weight: " + weight + ", Rating: " + rating;
    }
}
