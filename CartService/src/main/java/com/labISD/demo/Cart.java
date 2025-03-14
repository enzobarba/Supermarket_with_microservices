package com.labISD.demo;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Optional;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


@Entity
public class Cart {
    
    @Id @GeneratedValue(strategy = GenerationType.UUID) @Getter
    private UUID cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) 
    @Getter 
    private List <CartItem> items;  

    @Getter @Setter @NotNull(message = "user id cannot be null") @Column(unique = true)  
    private UUID userId;

    @Getter @Setter @NotNull(message = "user id cannot be null") @Min(value = 0, message = "total amount cannot be less than 0")
    private float totalAmount;


    protected Cart(){}

    public Cart(UUID userId){
        items = new ArrayList<>();
        this.userId = userId;
        totalAmount = 0;
    }

    @Override
    public String toString(){
        return String.format("cartId: %s, userId: %s, items: %s, totalAmount: %.2f", cartId, userId, items.toString(), totalAmount);
    }

    public void addItemToCart(UUID productId, String name, int quantity, float price){
        Optional<CartItem> existingItem = items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(this);
            newItem.setProductId(productId);
            newItem.setName(name);
            newItem.setQuantity(quantity);
            items.add(newItem);
        }
        totalAmount+= quantity*price;
    }

    public void removeItem(UUID productId){
        items.removeIf(item -> item.getProductId().equals(productId));
    }

    public void clear(){
        items.clear();
        totalAmount = 0;
    }

    public boolean isEmpty(){
        if(items.size() == 0){
            return true;
        }
        return false;
    }

}
