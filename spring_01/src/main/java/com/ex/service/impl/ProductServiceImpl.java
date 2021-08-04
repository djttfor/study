package com.ex.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.entity.Product;
import com.ex.mapper.ProductMapper;
import com.ex.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService{

    @Autowired
    ProductMapper productMapper;


    @Override
    //@Transactional
    public Boolean decrementCount(int pid, int count) {
        Product product = productMapper.selectOne(Wrappers.lambdaQuery(Product.class)
                .select(p -> "current_count".equals(p.getColumn())).eq(Product::getId, pid));

        Product p = Optional.ofNullable(product).orElseThrow(() -> new RuntimeException("商品不存在"));

        if(p.getCurrentCount().compareTo(count)<0){
            throw new RuntimeException("库存不足");
        }
        int update = productMapper.update(new Product(),
                Wrappers.lambdaUpdate(Product.class)
                        .setSql("current_count = current_count - " + count).eq(Product::getId,pid));
       // int a = 1/0;
        return update>0;
    }

    //@Transactional
    public Boolean decrementTotalCount(int pid, int count){
        Product product = productMapper.selectOne(Wrappers.lambdaQuery(Product.class)
                .select(p -> "total_count".equals(p.getColumn())).eq(Product::getId, pid));

        Product p = Optional.ofNullable(product).orElseThrow(() -> new RuntimeException("商品不存在"));

        if(p.getTotalCount().compareTo(count)<0){
            throw new RuntimeException("总库存不足");
        }
        int update = productMapper.update(new Product(),
                Wrappers.lambdaUpdate(Product.class)
                        .setSql("total_count = total_count - " + count).eq(Product::getId,pid));
        //int a = 1/0;
        return update>0;
    }

    @Override
    public Boolean updateProductName(int pid, String name) {
        return productMapper.updateById(new Product().setId(pid).setProductName(name))>0;
    }


}




