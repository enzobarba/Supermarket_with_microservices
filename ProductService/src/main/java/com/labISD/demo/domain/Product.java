package com.labISD.demo.domain;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import com.labISD.demo.enums.CATEGORY;

@Entity
public class Product {
    
    @Id @GeneratedValue(strategy = GenerationType.UUID) @Getter
    private UUID id;
  
    @NotNull(message = "name cannot be null")
    @Getter @Setter
    @Size(min = 2, max = 20, message = "length of name must be 2-20") 
    @Column(unique = true)
    private String name;

    @NotNull(message = "price cannot be null")
    @Getter @Setter 
    @Min(value = 0, message = "price must be greater than or equal to 0") 
    private float price;

    @NotNull(message = "quantity available cannot be null")
    @Getter @Setter 
    @Min(value = 0, message = "quantity available must be greater than or equal to 0") 
    private int quantityAvailable;

    @NotNull(message = "weight cannot be null")
    @Getter @Setter 
    @Min(value = 0, message = "weight must be greater than or equal to 0") 
    private float weight;

    @NotNull(message = "rating cannot be null")
    @Getter @Setter 
    @Min(value = 0, message = "rating must be greater than or equal to 0")
    @Max(value = 5, message = "rating must be less than or equal to 5") 
    private float rating;

    @NotNull(message = "quantityRatings cannot be null") 
    @Min(value = 0, message = "quantityRatings must be greater than or equal to 0") 
    private int quantityRatings;

    @NotNull(message = "category cannot be null")
    @Getter @Setter 
    private CATEGORY category;

    protected Product(){}

    public Product(String name, float price, int quantityAvailable, float weight, CATEGORY category){
        this.name = name;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.weight = weight;
        this.rating = 0;
        this.quantityRatings = 0;
        this.category = category;
    }

    @Override
    public String toString(){
        return String.format("ID: %s, Category: %s, Name: %s, Price: %.2f €, Quantity available: %d, Weight: %.2f Kg, Rating: %.2f", id, category, name, price, quantityAvailable, weight, rating);
    }

    public void addRating(int rating){
        quantityRatings++;
        this.rating = this.rating + (rating - this.rating)/quantityRatings;
    }

    public boolean quantityAvailable(int quantity){
        if(quantity <= this.quantityAvailable){
            return true;
        }
        return false;
    }

    public void supply(int quantity){
        this.quantityAvailable+= quantity;
    }

    public void buy(int quantity){
        this.quantityAvailable-= quantity;
    }
}
