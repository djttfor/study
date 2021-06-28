package com.ex.routing;

import com.ex.util.ConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 3; i++) {
            int j = i+1;
            new Thread(()->{
                consum("queue1","consumer"+j);
            }, ("queue"+j)).start();
        }
        while (Thread.activeCount()>2){
            Thread.yield();
        }
    }
    public static void consum(String queueName,String consumerName){
        consum(queueName,consumerName,null);
    }
    public static void consum(String queueName,String consumerName,Long sleepTime) {
        Connection connection =null;
        AtomicInteger count = new AtomicInteger();
        try {
            //创建连接Connection
            connection = ConnectUtil.buildDefaultConnection(queueName);
            //通过连接获取通道Channel
            Channel channel = connection.createChannel();
            channel.basicQos(10);
            //消费
            channel.basicConsume(queueName, false, (consumerTag, message) -> {
                byte[] body = message.getBody();
                System.out.println(consumerName+"在"+queueName+"得到的消息："+new String(body));
                System.out.println(consumerName+"得到"+(count.incrementAndGet())+"条");
                if(sleepTime!=null){
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //手动提交
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }, consumerTag -> System.out.println(queueName+"消费失败"));
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectUtil.close(connection);
        }
    }
}
