package com.labISD.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.labISD.demo.domain.Product;

public interface ProductRepository extends CrudRepository <Product,Long>{
    
}
