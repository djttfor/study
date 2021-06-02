package com.ex.server.rabbit.producers;

import com.ex.server.entity.Employee;
import com.ex.server.rabbit.RabbitConstant;
import com.ex.server.service.MailLogService;
import com.ex.server.util.CommonUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailProducers {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MailLogService mailLogService;

    /**
     * 生产欢迎新员工的消息
     * @param employee
     */
    public void sendWelcomeMail(Employee employee){

        String uuid = CommonUtil.getUUID();
        //保存冗余
        mailLogService.saveRedundant(employee,uuid);
        /*
         * 1.路由器
         * 2.路由key
         * 3.发送的消息实体
         * 4.消息id
         */
        rabbitTemplate.convertAndSend(RabbitConstant.MAIL_EX,RabbitConstant.MAIL_WELCOME,employee,new CorrelationData(uuid));
    }

    /**
     * 重发消息
     * @param employee
     * @param magId
     */
    public void resendMailMessage(Employee employee,String magId){
        rabbitTemplate.convertAndSend(RabbitConstant.MAIL_EX,RabbitConstant.MAIL_WELCOME,employee,new CorrelationData(magId));
    }

}
