package com.labISD.demo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.UUID) @Getter
    private UUID id;

    @ManyToOne @Setter @JoinColumn(name = "orderId", nullable = false)
    @NotNull(message = "order cannot be null")
    private Order order;

    @Getter @Setter @NotNull(message = "product ID cannot be null")
    private UUID productId;

    @Getter @Setter @NotNull(message = "name cannot be null")
    private String name;

    @Getter @Setter @NotNull(message = "quantity cannot be null")
    @Min(value = 1, message = "quantity must be greater than or equal to 1")
    private int quantity;

    @Getter @Setter @NotNull(message = "unit price cannot be null")
    @Min(value = 0, message = "unit price must be greater than or equal to 0")
    private float unitPrice;  

    @Getter @Setter @NotNull(message = "total price cannot be null")
    @Min(value = 0, message = "totalPrice must be greater than or equal to 0")
    private float totalPrice; 

    protected OrderItem() {}

    public OrderItem(UUID productId, Order order, String name, int quantity, float unitPrice) {
        this.productId = productId;
        this.order = order;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = quantity * unitPrice;
    }

    @Override
    public String toString() {
        return String.format("ProductId: %s, Name: %s, Quantity: %d, UnitPrice: %.2f€, TotalPrice: %.2f€",
                productId, name, quantity, unitPrice, totalPrice);
    }
}
