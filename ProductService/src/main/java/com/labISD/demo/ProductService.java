package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import com.labISD.demo.dto.ProductDTO;
import com.labISD.demo.enums.CATEGORY;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import java.util.stream.Collectors;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;


    public void addProduct(Product product){
        productRepository.save(product);
    }

    public List <Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void deleteProduct(UUID id){
        productRepository.deleteById(id);
    }

    public Product findProductByName(String name){
        return productRepository.findByName(name);
    }

    public List <Product> findProductsByAvailable(boolean available){
        return productRepository.findAll().stream()
        .filter(product ->product.isAvailable() == available)
        .collect(Collectors.toList());            
    }

    public List <Product> findProductsByCategory(CATEGORY category){
        return productRepository.findByCategory(category);
    }

    public List <Product> findAllProducts(){
        return productRepository.findAll();
    }

    public List <Product> getSortedProductsByRatingDesc(){
        return productRepository.findAll(Sort.by(Sort.Order.desc("rating")));
    }

    public List <Product> getSortedProductsByRatingAsc(){
        return productRepository.findAll(Sort.by(Sort.Order.asc("rating")));
    }

    public List <Product> getSortedProductsByPriceAsc(){
        return productRepository.findAll(Sort.by(Sort.Order.asc("price")));
    }

    public List <Product> getSortedProductsByPriceDesc(){
        return productRepository.findAll(Sort.by(Sort.Order.desc("price")));
    }

    public void supplyProduct(UUID id, int quantity){
        Optional <Product> product = productRepository.findById(id);
        product.ifPresent(p -> {p.supply(quantity);
                        productRepository.save(p);
        });
    }

    public ProductDTO getNameQuantityProduct(UUID productId){
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            Product p = product.get();
            return (new ProductDTO(p.getName(), p.getQuantity()));
        }
        else
            return null;
    }

    public void rateProduct(UUID id, int rating){
        Optional <Product> product = productRepository.findById(id);
        product.ifPresent(p -> {p.addRating(rating);
                        productRepository.save(p);
        });
    }
    
}
