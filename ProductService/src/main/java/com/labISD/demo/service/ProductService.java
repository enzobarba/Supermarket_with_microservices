package com.labISD.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.labISD.demo.dto.NewProductDTO;
import com.labISD.demo.enums.CATEGORY;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import java.util.Optional;
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

    public void supplyProduct(UUID id, int quantity){
        Optional <Product> product = productRepository.findById(id);
        product.ifPresent(p -> {p.supply(quantity);
                        productRepository.save(p);
        });
    }

    public void rateProduct(UUID id, int rating){
        Optional <Product> product = productRepository.findById(id);
        product.ifPresent(p -> {p.addRating(rating);
                        productRepository.save(p);
        });
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }
}
