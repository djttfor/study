package my.service;

import my.domain.Orders;

import java.util.List;

public interface OrdersService {
    /**
     * 分页查询所有订单
     * @param pageNum 当前页
     * @param pageSize 每页记录数
     * @return list结果集
     * @throws Exception
     */
    List<Orders> findAll(int pageNum,int pageSize) throws Exception;

    Orders findOrdersById(String id) throws Exception;
}
