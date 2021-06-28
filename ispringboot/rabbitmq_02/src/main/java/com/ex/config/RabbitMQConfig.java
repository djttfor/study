//package com.ex.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class RabbitMQConfig {
//    //直接模式交换机
//    @Bean
//    public DirectExchange buildExchange(){
//        return new DirectExchange("i_direct_ex",true,false);
//    }
//    //sms队列
//    @Bean
//    public Queue smsQueue(){
//        Map<String,Object> map = new HashMap<>();
//        //设置过期时间
//        //map.put("x-message-ttl",5000);
//
//        //设置最大消息数，超过最大消息数的消息会被放入死信队列中
//        map.put("x-max-length",5);
//        //死信交换机
//        map.put("x-dead-letter-exchange","dead_direct_ex");
//        //死信路由key
//        map.put("x-dead-letter-routing-key","dead");
//        return new Queue("smsQueue_direct",true,false,false,map);
//    }
//    //直接交换机绑定sms队列
//    @Bean
//    public Binding smsBinding(){
//        return BindingBuilder.bind(smsQueue()).to(buildExchange()).with("sms");
//    }
//
//    //死信交换机
//    @Bean
//    public DirectExchange buildDeadExchange(){
//        return new DirectExchange("dead_direct_ex",true,false);
//    }
//    //死信队列
//    @Bean
//    public Queue deadQueue(){
//        return new Queue("dead_direct_ex",true);
//    }
//    //绑定死信队列
//    @Bean
//    public Binding deadBinding(){
//        return BindingBuilder.bind(deadQueue()).to(buildDeadExchange()).with("dead");
//    }
//}
