package com.labISD.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.labISD.demo.dto.NewProductDTO;
import com.labISD.demo.dto.RateProductDTO;
import com.labISD.demo.dto.SupplyProductDTO;
import com.labISD.demo.enums.CATEGORY;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.labISD.demo.repository.ProductRepository;
import com.labISD.demo.domain.Product;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public String addProduct(NewProductDTO productDTO){
        if(productRepository.findByName(productDTO.name()) != null){
            return "Error: product already exists";
        }
        productRepository.save(new Product(productDTO.name(), productDTO.price(), productDTO.quantityAvailable(), productDTO.weight(), productDTO.category()));
        return "Product successfully added";
    }

    public Product getProductById(UUID id){
        return productRepository.findById(id).get();
    }

    public String getAllProducts(){
        List <Product> products = productRepository.findAll();
        if(products.size() == 0){
            return "no products";
        }
        return products.toString();
    }

    public String getProductsByCategory(CATEGORY category){
        List <Product> products = productRepository.findByCategory(category);
        if(products.size() == 0){
            return "no "+category+" products";
        }
        return products.toString();
    }

    public String getSortedProductsByRatingDesc(){
        return productRepository.findAll(Sort.by(Sort.Order.desc("rating"))).toString();
    }

    public String supplyProduct(SupplyProductDTO supplyProductDTO){
        Product product = productRepository.findByName(supplyProductDTO.name());
        if(product == null){
            return "ERROR: product not found";
        }
        product.supply(supplyProductDTO.quantity());
        productRepository.save(product);
        return String.format("[%s] product successfully supplied", supplyProductDTO.name());
    }

    public String rateProduct(RateProductDTO rateProductDTO){
        Product product = productRepository.findByName(rateProductDTO.name());
        if(product == null){
            return "ERROR: product not found";
        }
        product.addRating(rateProductDTO.rate());
        productRepository.save(product);
        return String.format("[%s] product successfully rated", rateProductDTO.name());
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public UUID getProductIdByName(String name){
        Product p = productRepository.findByName(name);
        if(p != null){
            return p.getId();
        }
        return null;
    }
}
