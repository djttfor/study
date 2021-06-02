package com.ex.server.itest;

import com.ex.server.rabbit.RabbitConstant;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

@SpringBootTest
public class Test3 {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test1(){
        //测试消息发送失败，只要发到不存在的交换机即可
        rabbitTemplate.convertAndSend("xxxx", RabbitConstant.MAIL_WELCOME,"hehe",new CorrelationData(UUID.randomUUID().toString()));
    }
    @Test
    public void test2() throws InterruptedException {
        rabbitTemplate.convertAndSend(RabbitConstant.MAIL_EX,"asdg","今晚打老虎",new CorrelationData(UUID.randomUUID().toString()));
        Thread.sleep(10000);
    }

    @Test
    public void test3(){
        String key = "239cc758-6522-4b99-90e5-20d1dc2db187";

    }
}
