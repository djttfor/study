package com.ex.ssm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ex.ssm.entity.Product;


import com.ex.ssm.mapper.ProductMapper;
import com.ex.ssm.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
