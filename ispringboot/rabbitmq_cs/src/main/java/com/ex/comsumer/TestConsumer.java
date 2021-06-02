package com.ex.comsumer;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.entity.IOrder;
import com.ex.entity.Rider;
import com.ex.service.OrderService;
import com.ex.service.RiderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class TestConsumer {
    @Autowired
    RiderService riderService;
    @Autowired
    OrderService orderService;


    /*
    解决因异常导致重试死循环的解决方案
    1.设置重试次数
    2.try+catch+手动Ack
    3.try+catch+手动Ack + 死信队列
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "test",durable = "true",autoDelete = "false",
                    arguments = {
                            @Argument(name="x-dead-letter-exchange",value = "deadEx"),
                            @Argument(name = "x-dead-letter-routing-key",value = "deadKey")
                    }),
                    exchange = @Exchange(name = "i_test",type = ExchangeTypes.TOPIC),
                    key = "*.order")
    })
    public void saveOrder(String orderNum, Channel channel, Message message) throws InterruptedException, IOException {
        log.info("获取订单：{}，开始配送",orderNum);
        Rider rider = new Rider();
        rider.setOrderNum(orderNum);
        rider.setStatus("0");
        rider.setRemark("开始配送");


        try {
            riderService.save(rider);
            log.info("保存配送消息成功");
            //假设配送成功
            handle(orderNum,"1","配送成功","订单已配送完成");
            //手动告诉mq已经正常消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            log.error("出现了异常：{}",e.getMessage());
            //参数1：消息的tag，参数2：多条处理，参数3：是否重发
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }
    }

    public void handle(String orderNum,String status,String riderMessage,String orderMessage){
        riderService.update(Wrappers.lambdaUpdate(Rider.class)
                .set(Rider::getStatus,status)
                .set(Rider::getRemark,riderMessage)
                .eq(Rider::getOrderNum,orderNum));
        int a= 1/0;
        orderService.update(Wrappers.lambdaUpdate(IOrder.class)
                .set(IOrder::getStatus,status)
                .set(IOrder::getRemark,orderMessage)
        );
        log.info("配送成功");
    }

    @RabbitListener(bindings ={
             @QueueBinding(
                     value = @Queue(name = "deadQueue", durable = "true", autoDelete = "false"),
                     exchange = @Exchange(name = "deadEx", type = ExchangeTypes.DIRECT),
                     key = "deadKey"
             )
    })
    public void deadHandle(Message message){
        log.info("这是一条死消息：{}",message);
        log.info("tap:{}",message.getMessageProperties().getDeliveryTag());
    }
}
