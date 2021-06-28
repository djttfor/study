package com.ex.routing;

import com.ex.util.ConnectUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {
    public static void main(String[] args) {
        Connection connection =null;
        try {
            //创建连接Connection
            connection = ConnectUtil.buildDefaultConnection("生产者");
            //通过连接获取通道Channel
            Channel channel = connection.createChannel();
            //定义交换机
            channel.exchangeDeclare("idrect", BuiltinExchangeType.DIRECT,true);
            //定义队列
            channel.queueDeclare("queue1",true,false,false,null);
            //交换机绑定队列
            channel.queueBind("queue1","idrect","");
            //发送消息
            for (int i = 0; i < 10; i++) {
                channel.basicPublish("idrect","",null,("今晚打老虎"+i).getBytes());
            }
            System.out.println("发送成功");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           ConnectUtil.close(connection);
        }
    }
}
