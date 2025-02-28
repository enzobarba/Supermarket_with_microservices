package com.labISD.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.labISD.demo.domain.Product;
import java.util.UUID;

public interface ProductRepository extends CrudRepository <Product, UUID>{
    
    
}
