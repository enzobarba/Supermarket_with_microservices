package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;
import com.labISD.demo.domain.Product;
import com.labISD.demo.service.ProductService;
import com.labISD.demo.enums.CATEGORY;
import java.util.UUID;

@SpringBootApplication
@RestController
public class SuperMarketApplication {

	@Autowired
	private ProductService productService;

	public static void main(String[] args) {
		SpringApplication.run(SuperMarketApplication.class, args);
	}

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
