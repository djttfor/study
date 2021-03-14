package my.service.impl;

import com.github.pagehelper.PageHelper;
import my.dao.OrdersDao;
import my.dao.ProductDao;
import my.domain.Orders;
import my.domain.Product;
import my.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    @Autowired
    OrdersDao ordersDao;

    @Override
    public List<Product> findAll() {
        //PageHelper.startPage();
        return productDao.findAll();
    }

    @Override
    /**
    * Description:
    * <分页查询产品>
    * @param pageNum
    * @param pageSize
    * @return: java.util.List<my.domain.Product>
    * @Author: DJ
    * @Date: 2020-03-29 16:40
    */
    public List<Product> findAllPaging(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return productDao.findAll();
    }

    @Override
    public int saveProduct(Product product) {
        return productDao.saveProduct(product);
    }

    @Override
    public Product findById(String id) throws Exception {
        return productDao.findProductById(id);
    }

    @Override
    public int deleteProduct(String id) throws Exception {
        //先查出有哪些订单关联了此产品,如果有就把它们全部删除
        List<Orders> orders = ordersDao.findOrdersByProductId(id);
        int result = 0;
        if( orders!=null && orders.size()>0){
            //删除订单前得删除与旅客表多对多的关系
            for (Orders order : orders) {
                ordersDao.deleteOMT(order.getId());
            }
            //然后删除所有关联的订单然后再删除产品
            for (Orders order : orders) {
                ordersDao.deleteOrder(order.getId());
            }
            //然后再删除产品
            result = productDao.deleteProduct(id);
        }else{
            System.out.println("该产品没有订单");
            result = productDao.deleteProduct(id);
        }
        return result;
    }

    @Override
    public int delSelected(String pids) throws Exception {
        //解析pids
        String[] ids = pids.split("@");
        int result = 0;
        for (String id : ids) {
            result = deleteProduct(id);
        }
        return result;
    }
}
