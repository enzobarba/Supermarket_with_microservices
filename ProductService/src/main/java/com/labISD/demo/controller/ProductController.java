package com.labISD.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.*;
import com.labISD.demo.service.ProductService;
import com.labISD.demo.enums.CATEGORY;

@RestController
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
	public String addProduct(@RequestBody NewProductDTO productDTO){
		return productService.addProduct(productDTO);
	}

	@GetMapping("/getAllProducts")
	public String getAllProducts(){
		return productService.getAllProducts();
	}

	@GetMapping("/getSortedProductsByRatingDesc")
	public String getSortedProductsByRatingDesc(){
		return productService.getSortedProductsByRatingDesc();
	}

	@GetMapping("/getProductsByCategory")
	public String getProductsByCategory(@RequestParam(value = "category") CATEGORY category){
		return productService.getProductsByCategory(category);
	}

	@PutMapping("/supplyProduct")
	public String supplyProduct(@RequestBody SupplyProductDTO supplyProductDTO){
		return productService.supplyProduct(supplyProductDTO);
	}

	@PutMapping("/rateProduct")
	public String rateProduct(@RequestBody RateProductDTO rateProductDTO){
		return productService.rateProduct(rateProductDTO);
	}

}
