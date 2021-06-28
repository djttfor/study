package com.ex.c;

import com.ex.pojo.Brand;
import com.ex.service.BrandService;
import com.ex.service.DemoService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;


public class Consumer {

    public static void main(String[] args) throws IOException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("consumer.xml");
        BrandService brandService = (BrandService) ac.getBean("brandService");
        List<Brand> all = brandService.findAll();
        for (Brand brand : all) {
            System.out.println(brand);
        }

        System.in.read();
    }
}
