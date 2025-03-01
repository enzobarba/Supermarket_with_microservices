package com.labISD.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.domain.Product;
import com.labISD.demo.enums.CATEGORY;
import com.labISD.demo.service.ProductService;

@RestController
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("/addProduct")
	public void addProduct(){
		productService.addProduct(new Product("Pettodipollo", 2, 10, 2, CATEGORY.Meat));
		productService.addProduct(new Product("Pettoditacchino", 3, 10, 2, CATEGORY.Meat));
		productService.addProduct(new Product("spaghetti", 1.50f, 30, 1, CATEGORY.Pasta));
	}

	@GetMapping("/getProduct")
	public String getProducts(){
		List <Product> products = new ArrayList<>();
		productService.findAllProducts().forEach(products::add);
		return "Products found: " + products.toString();
	}

	@GetMapping("/getSortedProduct")
	public String getSortedProducts(){
		List <Product> products = new ArrayList<>();
		productService.getSortedProductsByPriceAsc().forEach(products::add);
		return "Products found: " + products.toString();
	}

	@GetMapping("/buyProduct")
	public void buyProduct(@RequestParam(value = "id") UUID id, @RequestParam(value = "q") int q){
		productService.buyProduct(id, q);
	}

	@GetMapping("/supplyProduct")
	public void supplyProduct(@RequestParam(value = "id") UUID id, @RequestParam(value = "q") int q){
		productService.supplyProduct(id, q);
	}

	@GetMapping("/rateProduct")
	public void rateProduct(@RequestParam(value = "id") UUID id, @RequestParam(value = "r") int r){
		productService.rateProduct(id, r);
	}
}
