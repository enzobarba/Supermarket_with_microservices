package com.labISD.demo.dto;

import com.labISD.demo.enums.CATEGORY;

public record NewProductDTO (String token, String name, float price, int quantityAvailable, float weight, CATEGORY category) {
    
}
