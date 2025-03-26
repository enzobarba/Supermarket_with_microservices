package com.labISD.demo.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.labISD.demo.enums.CATEGORY;
import com.labISD.demo.domain.Product;

public interface ProductRepository extends JpaRepository <Product, UUID>{
    
    public Product findByName(String name);
    public List <Product> findByCategory(CATEGORY category);
    public List <Product> findAll(Sort sort);
    
}
