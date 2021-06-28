package com.ex.test;

import com.ex.entity.Rider;
import com.ex.service.RiderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test1 {
    @Autowired
    RiderService riderService;
    @Autowired
    Rider rider;


    @Test
    public void test(){
        System.out.println(rider);
    }
}
