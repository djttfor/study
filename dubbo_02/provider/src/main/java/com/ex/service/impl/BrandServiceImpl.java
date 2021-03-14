package com.ex.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;

import com.alibaba.dubbo.config.annotation.Service;
import com.ex.dao.BrandMapper;
import com.ex.pojo.Brand;
import com.ex.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import javax.annotation.Resource;
import java.util.List;
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandMapper brandMapper;

    public List<Brand> findAll() {
//        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext-dao.xml");
//        BrandMapper brandMapper = (BrandMapper) ac.getBean("brandMapper");
        return brandMapper.selectAll();
    }

    public Brand findByBrandId() {
        Brand brand = new Brand();
        brand.setId(16);
        brand.setImage("a.png");
        brand.setLetter("A");
        brand.setName("Apple");
        brand.setSep(2);
        return brand;
    }
}
