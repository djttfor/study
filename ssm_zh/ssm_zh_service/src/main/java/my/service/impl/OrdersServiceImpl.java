package my.service.impl;

import com.github.pagehelper.PageHelper;
import my.dao.OrdersDao;
import my.domain.Orders;
import my.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersDao ordersDao;
    @Override
    public List<Orders> findAll(int pageNum,int pageSize) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        return ordersDao.findAll();
    }

    @Override
    public Orders findOrdersById(String id) throws Exception {
        return ordersDao.findOrdersById(id);
    }
}
