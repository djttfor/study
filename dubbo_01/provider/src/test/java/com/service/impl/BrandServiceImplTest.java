package com.service.impl;

import com.pojo.Brand;
import com.service.BrandService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-dao.xml")
public class BrandServiceImplTest extends TestCase {

    @Autowired
    BrandService brandService;
    @Test
    public void testFindAll() {
        List<Brand> all = brandService.findAll();
        for (Brand brand : all) {
            System.out.println(brand);
        }
    }
}