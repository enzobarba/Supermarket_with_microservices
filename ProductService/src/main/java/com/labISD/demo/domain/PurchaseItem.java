package com.labISD.demo.domain;

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
public class PurchaseItem {
    
    @Id @GeneratedValue (strategy = GenerationType.UUID) @Getter
    private UUID id;

    @ManyToOne @Setter @Getter
    @JoinColumn(name = "purchaseId", nullable = false)
    private Purchase purchase;

    @JoinColumn(name = "productId", nullable = false) 
    @ManyToOne @Setter @Getter
    private Product product;

    @Getter @Setter @NotNull(message = "quantity cannot be null") @Min(value = 1, message = "quantity must be greater than or equal to 1") 
    private int quantity;
 
    @Getter @Setter @Min(value = 0, message = "total price must bre greater than or equal to 0")
    private float totalPrice;


    protected PurchaseItem(){}

    public PurchaseItem(Product product, Purchase purchase, int quantity) {
        this.product = product;
        this.purchase = purchase;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;  
    }

    public void addQuantity(int quantity) {
        this.quantity+= quantity;
    }  

    public void updateTotalPrice() {
        this.totalPrice = product.getPrice() * this.quantity;
    }     

    @Override
    public String toString(){
        return String.format("id: %s, product: %s, rating: %.2f, weight: %.2f KG, quantity: %d, total price: %.2f EUR",id, product.getName(), product.getRating(), product.getWeight(), quantity, totalPrice);
    }
}