package my.dao;


import my.domain.Orders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class OrdersDaoTest {
    @Autowired
    OrdersDao ordersDao;
    @Test
    public void findAll() throws Exception {
        List<Orders> all = ordersDao.findAll();
        for (Orders orders : all) {
            System.out.println(orders);
        }
    }
    @Test
    public void findOrdersById() throws Exception {
        Orders orsers = ordersDao.findOrdersById("1A0BF2146D9A11EA9194000C29C426BD" +
                "");
        System.out.println(orsers);
    }
    @Test
    public void findOrdersByProductId() throws Exception {
        List<Orders> orders = ordersDao.findOrdersByProductId("49E31F996E1911EA9194000C29C426BD");
        for (Orders order : orders) {
            System.out.println(order.getId());
        }
    }
    @Test
    public void deleteOMT(){
        int i = ordersDao.deleteOMT("1A0BF2146D9A11EA9194000C29C426BD");
        System.out.println(i);
    }
    @Test
    public void deleteOrder(){
        int i = ordersDao.deleteOrder("1A0BF2146D9A11EA9194000C29C426BD");
        System.out.println(i);
    }
}
