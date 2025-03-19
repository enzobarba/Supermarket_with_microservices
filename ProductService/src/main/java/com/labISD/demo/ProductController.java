package com.labISD.demo;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.labISD.demo.dto.*;
import com.labISD.demo.enums.CATEGORY;

@RestController
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
	public String addProduct(@RequestBody ProductDTO productDTO){
		return productService.addProduct(productDTO);
	}

	@GetMapping("/getAllProducts")
	public String getAllProducts(){
		return productService.getAllProducts();
	}

	@GetMapping("/getSortedProductsByRatingDesc")
	public String getSortedProducts(){
		return productService.getSortedProductsByRatingDesc();
	}

	@GetMapping("/getProductsByCategory")
	public String getProductsByCategory(CATEGORY category){
		return productService.getProductsByCategory(category);
	}

	@PostMapping("/getProductCartDTO")
	public ProductCartDTO getProductCartDTO(@RequestBody UUID productId){
		return productService.getProductCartDTO(productId);
	}

	@GetMapping("/supplyProduct")
	public void supplyProduct(@RequestParam(value = "id") UUID id, @RequestParam(value = "q") int q){
		productService.supplyProduct(id, q);
	}

	//CHECK FROM HERE

	@GetMapping("/rateProduct")
	public void rateProduct(@RequestParam(value = "id") UUID id, @RequestParam(value = "r") int r){
		productService.rateProduct(id, r);
	}

	@PostMapping("/productsAvailable")
	public boolean productsAvailable(@RequestBody List <ProductAvailableDTO> products){
		return productService.productsAvailable(products);
	}

	@PostMapping("/decreaseProductsQuantity")
	public void decreaseProductsQuantity(@RequestBody List <ProductAvailableDTO> products){
		productService.decreaseProductsQuantity(products);
	}
}
