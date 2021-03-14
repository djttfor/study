package my.service;


import my.domain.Orders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class OrdersServiceTest {
    @Autowired
    OrdersService ordersService;
    @Test
    public void findAll() throws Exception {
        List<Orders> all = ordersService.findAll(1,4);
        for (Orders orders : all) {
            System.out.println(orders);
        }
    }
}
