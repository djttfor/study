package my.dao;


import my.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class ProductDaoTest {
    @Autowired
    ProductDao productDao;
    @Test
    public void findAll(){
        List<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }
   @Test
   public void test1(){
       ApplicationContext ac = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
       Product product = ac.getBean(Product.class);
       product.setCityName("北京");
       System.out.println(product);
   }
    @Test
    public void test2(){
        Product product = new Product();
        System.out.println(product);
    }
    @Test
    public void saveProduct(){
        Product product = new Product();
        product.setCityName("北京");
        //product.setDepartureTime(new Date());
        product.setProductName("伊拉克十二日游");
        product.setProductNumber("ylk118");
        product.setProductPrice(8888.0);
        product.setProductDesc("非常好玩");
        product.setProductStatus(1);
        int i = productDao.saveProduct(product);
        System.out.println(i);
    }
    @Test
    public void findProductById() throws Exception {
        Product p = productDao.findProductById("13528C8A6D8D11EA");
        System.out.println(p);
    }
}
