package com.ex.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {
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
            connection = connectionFactory.newConnection("消费者");
            //通过连接获取通道Channel
            channel = connection.createChannel();
            //通过创建交换机，生命队列，绑定关系，路由key，发送消息和接受消息
            String queueName = "queue1";

            channel.basicConsume(queueName, true, (consumerTag, message) -> {
                System.out.println(consumerTag);
                System.out.println("收到的消息：" + new String(message.getBody()));
            }, consumerTag -> {
                System.out.println("接收失败了");
            });
            System.in.read();
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
