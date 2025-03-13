package com.labISD.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.labISD.demo.dto.ProductAvailableDTO;
import com.labISD.demo.dto.ProductDTO;
import com.labISD.demo.enums.CATEGORY;

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

	@GetMapping("/getAllProducts")
	public String getAllProducts(){
		return productService.getAllProducts().toString();
	}

	@GetMapping("/getSortedProducts")
	public String getSortedProducts(){
		List <Product> products = new ArrayList<>();
		productService.getSortedProductsByPriceAsc().forEach(products::add);
		return "Products found: " + products.toString();
	}

	@PostMapping("/getProductDTO")
	public ProductDTO getProductDTO(@RequestBody UUID productId){
		return productService.getProductDTO(productId);
	}

	@GetMapping("/supplyProduct")
	public void supplyProduct(@RequestParam(value = "id") UUID id, @RequestParam(value = "q") int q){
		productService.supplyProduct(id, q);
	}

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
