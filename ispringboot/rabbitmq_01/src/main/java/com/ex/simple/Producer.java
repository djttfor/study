package com.ex.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.FanoutExchange;

import java.io.IOException;

public class Producer {
    public static void main(String[] args) {
        Connection connection =null;
        Channel channel =null;
        try {
            //创建连接
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setUsername("admin");
            connectionFactory.setPassword("admin");
            connectionFactory.setPort(5672);
            connectionFactory.setHost("47.112.181.157");
            connectionFactory.setVirtualHost("/");
            //创建连接Connection
            connection = connectionFactory.newConnection("生产者");
            //通过连接获取通道Channel
            channel = connection.createChannel();
            //通过创建交换机，生命队列，绑定关系，路由key，发送消息和接受消息
            String queueName = "queue1";
            channel.queueDeclare(queueName,false,false,false,null);

            FanoutExchange fanoutExchange = new FanoutExchange("fanoutEx");

            //准备消息内容
            //发送消息队列
            channel.basicPublish("",queueName,null,"今晚打老虎".getBytes());
            System.out.println("发送成功");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            if(connection!=null && connection.isOpen()){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
