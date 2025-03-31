package com.labISD.demo.dto;

import com.labISD.demo.enums.CATEGORY;

public record NewProductDTO (String name, float price, int quantityAvailable, float weight, CATEGORY category) {
    
}
