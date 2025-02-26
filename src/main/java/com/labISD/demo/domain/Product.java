package com.labISD.demo.domain;

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
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Getter
    private Long id;

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
}
