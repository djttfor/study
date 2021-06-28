package my.dao;

import my.domain.Member;
import my.domain.Orders;
import my.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrdersDao {

    /*@Select("select * from orders")
    @Results( id = "ordersMap" ,value = {
            @Result(property = "id",column = "id" ,id = true),
            @Result(property = "orderNumber",column = "order_number"),
            @Result(property = "orderTime",column = "order_time"),
            @Result(property = "peopleCount",column = "people_count"),
            @Result(property = "orderDesc",column = "order_desc"),
            @Result(property = "payType",column = "pay_type"),
            @Result(property = "orderStatus",column = "order_status"),
            @Result(property = "product",column = "product_id",javaType = Product.class,
            one = @One(select = "my.dao.ProductDao.findProductById"))
    }
    )*/
    /**
    * Description:
    * <分页查询>
    * @return: java.util.List<my.domain.Orders>
    * @Author: DJ
    * @Date: 2020-03-30 21:43
    */
    List<Orders> findAll() throws Exception;
    /**
     * Description:
     * <根据orderID查出订单详情>
     * @param id orderId
     * @return: orders 结果集
     * @Author: DJ
     * @Date: 2020-03-30 21:44
     */
    Orders findOrdersById(String id) throws Exception;


    List<Orders> findOrdersByProductId(String pid) throws Exception;

    int deleteOMT(String id);

    int deleteOrder(String id);
}
