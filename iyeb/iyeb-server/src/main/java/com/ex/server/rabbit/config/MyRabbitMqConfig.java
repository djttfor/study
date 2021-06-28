package com.ex.server.rabbit.config;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ex.server.entity.MailLog;
import com.ex.server.rabbit.RabbitConstant;
import com.ex.server.service.MailLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class MyRabbitMqConfig {

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    MailLogService mailLogService;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        //设置消息回调
        /*
        1.注意存有消息id
        2.ack表示消息是否发送成功
        3.发送失败原因
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            String msgId = correlationData.getId();
            if(ack){
                log.info("{}====>消息发送成功",msgId);
                //修改冗余表消息状态
                mailLogService.update(new MailLog(),new LambdaUpdateWrapper<>(MailLog.class)
                        .set(MailLog::getStatus,RabbitConstant.MESSAGE_SUCCESS).eq(MailLog::getMsgId,msgId));
            }else{
                log.info("{}====>交换机不存在",msgId);
                log.info("原因====>{}",cause);
                mailLogService.update(new MailLog(),new LambdaUpdateWrapper<>(MailLog.class)
                        .set(MailLog::getStatus,RabbitConstant.MESSAGE_FAIL).eq(MailLog::getMsgId,msgId));
            }
        });

        rabbitTemplate.setReturnsCallback(returned -> log.info("消息发送到队列失败====>{}",returned.toString()));
        rabbitTemplate.setMandatory(true);

        return rabbitTemplate;
    }

    @Bean
    public TopicExchange buildExchange(){
        return new TopicExchange(RabbitConstant.MAIL_EX,true,false);
    }

    @Bean
    public Queue mailQueue(){
        Map<String,Object> map = new HashMap<>();
        //死信交换机
        map.put("x-dead-letter-exchange",RabbitConstant.MAIL_DEAD_EX);
        //死信路由key
        map.put("x-dead-letter-routing-key",RabbitConstant.MAIL_DEAD_ROUTING_KEY);
        return new Queue(RabbitConstant.MAIL_QUEUE,true,false,false,map);
    }

    @Bean
    public Binding mailBinding(){
        return BindingBuilder.bind(mailQueue()).to(buildExchange()).with(RabbitConstant.MAIL_ROUTING_KEY);
    }

    /**
     * 死信交换机
     * @return
     */
    @Bean
    public DirectExchange mailDeadExchange(){
        return new DirectExchange(RabbitConstant.MAIL_DEAD_EX,true,false);
    }
    @Bean
    public Queue mailDeadQueue(){
        return new Queue(RabbitConstant.MAIL_DEAD_QUEUE,true);
    }
    @Bean
    public Binding mailDeadBinding(){
        return BindingBuilder.bind(mailDeadQueue()).to(mailDeadExchange()).with(RabbitConstant.MAIL_DEAD_ROUTING_KEY);
    }
}
