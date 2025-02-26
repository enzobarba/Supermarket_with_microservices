package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;

import com.labISD.demo.domain.Product;
import com.labISD.demo.repository.ProductRepository;

@SpringBootApplication
@RestController
public class SuperMarketApplication {

	@Autowired
	private ProductRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SuperMarketApplication.class, args);
	}

	@GetMapping("/addProduct")
	public void addProduct(){
		repository.save(new Product("Petto di pollo", 3.2f, 20, 0.540f, 3));
	}

	@GetMapping("/getProduct")
	public String findProduct(){
		List <Product> products = new ArrayList<>();
		repository.findAll().forEach(products::add);
		return "Products found: " + products.toString();
	}
}
