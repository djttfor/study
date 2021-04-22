package com.ex.asyn;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.entity.IOrder;
import com.ex.entity.OrderRy;
import com.ex.service.OrderRyService;
import com.ex.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserServiceProducer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRyService orderRyService;

    //模拟下单
    public void makeOrder(String uid,String productId,Integer num){
        //模拟订单id
        for (int i = 0; i < 6; i++) {
            String orderId = UUID.randomUUID().toString();
            rabbitTemplate.convertAndSend("i_direct_ex","sms",orderId);
        }
        System.out.println("订单派发成功=========》");
    }
    public void sendOrder(){
        //生成订单
        String orderNum = UUID.randomUUID().toString();
        log.info("生成订单号：{}",orderNum);
        IOrder iOrder = new IOrder();
        iOrder.setOrderNum(orderNum);
        iOrder.setStatus("0");//表示开始配送
        log.info("订单状态为：{}",iOrder.getStatus());
        iOrder.setUserId(1);
        iOrder.setRemark("炸鸡+薯条￥25.5");
        orderService.save(iOrder);
        log.info("保存订单成功");

        //消息冗余
        OrderRy orderRy = new OrderRy(orderNum,"0");
        orderRyService.save(orderRy);
        log.info("保存订单冗余成功：{}",orderRy);

        //发送订单消息给配送中心
        rabbitTemplate.convertAndSend("i_test","a.order",orderNum);

        //设置回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            //如果发送消息成功
            if(ack){
                log.info("消息发送成功");
                //将冗余表的状态改为发送成功
                orderRyService.update(Wrappers.lambdaUpdate(OrderRy.class)
                        .set(OrderRy::getStatus,"1").eq(OrderRy::getOrderNum,orderNum));
            }else{
                log.info("消息发送失败");
                //将冗余表的状态改为发送成功
                orderRyService.update(Wrappers.lambdaUpdate(OrderRy.class)
                        .set(OrderRy::getStatus,"2").eq(OrderRy::getOrderNum,orderNum));
            }
        });
        log.info("发送了一条订单，订单号为：{}",orderNum);
    }
}
