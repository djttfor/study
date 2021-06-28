package com.ex.service;

import com.ex.pojo.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> findAll();
    Brand findByBrandId();//模拟
}
