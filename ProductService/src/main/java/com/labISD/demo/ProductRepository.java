package com.labISD.demo;

import java.util.UUID;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.labISD.demo.enums.CATEGORY;

public interface ProductRepository extends JpaRepository <Product, UUID>{
    
    public Product findByName(String name);
    public List <Product> findByCategory(CATEGORY category);
    public List <Product> findAll(Sort sort);
    
}
