package com.ex.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.entity.Product;
import com.ex.service.ProductService;
import com.ex.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductTest {
    @Autowired
    ProductService productService;

    @Autowired(required = false)
    ProductMapper productMapper;

    @Autowired
    UserService userService;

    @Test
    public void test(){
        System.out.println(productService.update(Wrappers.lambdaUpdate(Product.class)
                .setSql("current_count = current_count - 1")
                .eq(Product::getId, 3)
                .and(t->t.gt(Product::getCurrentCount,0)))
        );
    }
    @Test
    public void test2(){
        System.out.println(productService.decrementCount(1, 200));
    }

    @Test
    public void test3(){
        System.out.println(productService.decrementCount(1, 200));
    }

    @Test
    public void test4(){
        System.out.println(productService.decrementTotalCount(1, 200));
    }

    @Test
    public void test5(){
        System.out.println(productService.updateProductName(2, "蜜雪冰城"));
    }

    @Test
    public void test6(){
        System.out.println(productService.decrementCount(1, 200));
    }

    @Test
    public void test7(){
        System.out.println(userService.doDecrementCount(1, 200));
    }
}
