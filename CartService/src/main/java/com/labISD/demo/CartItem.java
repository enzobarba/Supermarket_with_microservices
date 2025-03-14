package com.labISD.demo;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class CartItem {
    
    @Id @GeneratedValue (strategy = GenerationType.UUID) @Getter
    private UUID id;

    @ManyToOne @Setter
    @JoinColumn(name = "cartId", nullable = false)
    private Cart cart;

    @Getter @Setter
    private UUID productId;

    @Getter @Setter @NotNull(message = "quantity cannot be null") @Min(value = 1, message = "quantity must be greater than or equal to 1") 
    private int quantity;

    @Getter @Setter @NotNull(message = "name cannot be null") 
    private String name;

    @Getter @Setter @NotNull(message = "unit price cannot be null") @Min(value = 0, message = "unit price must be greater than 0") 
    private float unitPrice;

    @Getter @Setter @NotNull(message = "total price cannot be null") @Min(value = 0, message = "total price must be greater than 0") 
    private float totalPrice;


    protected CartItem(){}

    public CartItem(UUID productId, Cart cart, int quantity, String name, float unitPrice){
        this.productId = productId;
        this.cart = cart;
        this.quantity = quantity;
        this.name = name;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice * quantity;
    }       

    @Override
    public String toString(){
        return String.format("id: %s, productId: %s, name: %s, quantity: %s, unit price: %.2f€, total price: %.2f€", id, productId, name, quantity, unitPrice, totalPrice);
    }
}
