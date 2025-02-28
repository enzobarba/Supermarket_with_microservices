package com.labISD.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.labISD.demo.domain.Product;
import java.util.UUID;
import java.util.List;
import org.springframework.data.domain.Sort;
import com.labISD.demo.enums.CATEGORY;

public interface ProductRepository extends CrudRepository <Product, UUID>{
    
    public List <Product> findByName(String name);
    public List <Product> findByAvialable(boolean avialable);
    public List <Product> findByCategory(CATEGORY category);
    public List <Product> findAll(Sort sort);

}
