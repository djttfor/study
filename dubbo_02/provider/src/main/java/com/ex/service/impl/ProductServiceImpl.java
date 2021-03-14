package com.ex.service.impl;

import com.ex.dao.ProductMapper;
import com.ex.pojo.Brand;
import com.ex.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Override
    public Brand findBrandByProductId() {
        return productMapper.findBrandByProductId();
    }
}
