package com.ex.test;

import com.ex.service.OrderRyService;
import com.ex.service.OrderService;
import com.ex.asyn.UserServiceProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test1 {

    @Autowired
    UserServiceProducer userService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRyService orderRyService;

    @Test
    public void test1(){
        userService.makeOrder("1","2",3);
    }


    @Test
    public void test2(){
        System.out.println(orderRyService.list());
    }

    @Test
    public void test3() throws InterruptedException {
        userService.sendOrder();
        //Thread.sleep(10000);
    }
}
