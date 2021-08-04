package com.ex.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ex.entity.Product;

/**
 *
 */
public interface ProductService extends IService<Product> {
    Boolean decrementCount(int pid,int count);
    Boolean decrementTotalCount(int pid,int count);
    Boolean updateProductName(int pid, String name);
}
