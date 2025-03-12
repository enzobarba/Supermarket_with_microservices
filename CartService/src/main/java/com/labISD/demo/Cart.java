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
import jakarta.validation.constraints.NotNull;


@Entity
public class Cart {
    
    @Id @GeneratedValue(strategy = GenerationType.UUID) @Getter
    private UUID cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) 
    @Getter 
    private List <CartItem> items;  

    @Getter @Setter @NotNull(message = "userId cannot be null") @Column(unique = true)  
    private UUID userId;

    protected Cart(){}

    public Cart(UUID userId){
        items = new ArrayList<>();
        this.userId = userId;
    }

    @Override
    public String toString(){
        return String.format("cartId: %s, userId: %s, items: %s", cartId, userId, items.toString());
    }

    public void addItemToCart(UUID productId, String name, int quantity){
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
    }

    public void removeItem(UUID productId){
        items.removeIf(item -> item.getProductId().equals(productId));
    }

    public void clear(){
        items.clear();
    }
}
