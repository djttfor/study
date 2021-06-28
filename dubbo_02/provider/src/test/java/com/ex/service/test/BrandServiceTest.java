package com.ex.service.test;

import com.ex.pojo.Brand;
import com.ex.service.BrandService;
import com.ex.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-dao.xml")
public class BrandServiceTest {
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    @Test
    public void findAll(){
        List<Brand> all = brandService.findAll();
        for (Brand brand : all) {
            System.out.println(brand);
        }
    }
    @Test
    public void findBrandByProductId(){
        System.out.println(productService.findBrandByProductId());
    }
}
