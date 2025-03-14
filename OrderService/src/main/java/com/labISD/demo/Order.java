package com.labISD.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;

@Entity
@Table(name = "orders") //Order is a reserved keyword for DB, like User
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.UUID) @Getter
    private UUID orderId;

    @Getter @Setter @NotNull(message = "user ID cannot be null")
    private UUID userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter @NotNull(message = "items cannot be null")
    private List<OrderItem> items;

    @Getter @Setter @NotNull(message = "Total amount cannot be null")
    @Min(value = 0, message = "Total amount must be greater than 0")
    private float totalAmount;

    protected Order() {}

    public Order(UUID userId, float totalAmount) {
        this.userId = userId;
        this.items = new ArrayList<>();
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return String.format("OrderId: %s, UserId: %s, Items: %s, Total amount: %.2f€", orderId, userId, items, totalAmount);
    }
}
