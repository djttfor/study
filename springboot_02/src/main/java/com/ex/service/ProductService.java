package com.ex.service;

import com.ex.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface ProductService extends IService<Product> {
    Boolean decrementCount(int pid,int count);
    Boolean decrementTotalCount(int pid,int count);
    Boolean updateProductName(int pid, String name);
}
