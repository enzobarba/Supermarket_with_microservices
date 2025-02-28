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
 
    @NotNull @Getter @Setter @Size(min = 2, max = 20) @Column(unique = true)
    private String name;

    @NotNull @Getter @Setter @Min(0)
    private float price;

    @NotNull @Getter @Setter @Min(0)
    private int quantity;

    @NotNull @Getter
    private boolean avialable;

    @NotNull @Getter @Setter @Min(0)
    private float weight;

    @NotNull @Getter @Setter @Min(0) @Max(5)
    private float rating;

    @NotNull @Getter @Setter @Min(0)
    private int quantityRatings;

    @NotNull @Getter @Setter 
    private CATEGORY category;

    protected Product(){}

    public Product(String name, float price, int quantity, float weight, CATEGORY category){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        if(quantity > 0){
            avialable = true;
        }
        else{
            avialable = false;
        }
        this.weight = weight;
        this.rating = 0;
        this.quantityRatings = 0;
        this.category = category;
    }

    @Override
    public String toString(){
        return "Id: " + id +", Category: " + category + ", Name: " + name + ", Price: " +price + ", Quantity: " + quantity + ", Weight (KG): " + weight + ", Rating: " + rating;
    }

    public int addRating(float rating){
        if(rating < 0 || rating > 5){
            return -1;
        }
        quantityRatings++;
        this.rating = this.rating + (rating - this.rating)/quantityRatings;
        return 0;
    }

    public void setAvialable(){
        if(quantity > 0){
            avialable = true;
        }
        else{
            avialable = false;
        }
    }

}
