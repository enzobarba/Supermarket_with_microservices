package com.labISD.demo;

import org.springframework.beans.factory.annotation.Autowired;
import com.labISD.demo.dto.ProductAvailableDTO;
import com.labISD.demo.dto.ProductCartDTO;
import com.labISD.demo.dto.ProductDTO;
import com.labISD.demo.enums.CATEGORY;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public String addProduct(ProductDTO productDTO){
        if(productRepository.findByName(productDTO.name()) != null){
            return "Error: product already exists";
        }
        productRepository.save(new Product(productDTO.name(), productDTO.price(), productDTO.quantity(), productDTO.weight(), productDTO.category()));
        return "Product successfully added";
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

    public ProductCartDTO getProductCartDTO(UUID productId){
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            Product p = product.get();
            return (new ProductCartDTO(p.getName(), p.getQuantity(), p.getPrice()));
        }
        else
            return null;
    }

    //CHECK FROM HERE

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

    public boolean productsAvailable(List <ProductAvailableDTO> products){
        return products.stream().allMatch( p -> {
            Product product = productRepository.findById(p.productId()).get();
            return (product != null && product.quantityAvailable(p.quantity()));
        });
    }

    public void decreaseProductsQuantity(List <ProductAvailableDTO> products){
        products.forEach( p -> {
            Product product = productRepository.findById(p.productId()).get();
            product.buy(p.quantity());
            productRepository.save(product);
        });
    }
    
}
