package com.ex.test;

import com.ex.config.MySpringConfig;
import com.ex.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MySpringConfig.class})
public class Test1 {
    @Autowired
    ProductService productService;

    @Test
    public void test1(){
        System.out.println(productService.updateProductName(3, "papa"));
    }
}
