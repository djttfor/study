package com.ex.dao;

import com.ex.pojo.Brand;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMapper {
    default Brand findBrandByProductId(){
        Brand brand = new Brand();
        brand.setId(16);
        brand.setImage("a.png");
        brand.setLetter("A");
        brand.setName("Apple");
        brand.setSep(2);
        return brand;
    }
}
