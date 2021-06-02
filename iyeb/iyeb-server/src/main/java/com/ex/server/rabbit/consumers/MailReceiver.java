package com.ex.server.rabbit.consumers;

import com.ex.server.entity.Employee;
import com.ex.server.rabbit.RabbitConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class MailReceiver {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @RabbitListener(queues = {RabbitConstant.MAIL_QUEUE})
    public void sendWelcomeMail(Message<Employee> message, Channel channel , org.springframework.amqp.core.Message msg) throws MessagingException, IOException {
        MessageProperties messageProperties = msg.getMessageProperties();
        byte[] body = msg.getBody();
        log.info("body:{}",new String(body));
        //获取消息
        Employee employee = message.getPayload();
        MessageHeaders headers = message.getHeaders();
        //tagId
        long tagId = messageProperties.getDeliveryTag();
        //获取msgId
        String msgId = (String) headers.get("spring_returned_message_correlation");
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        try {
            Map<Object, Object> entries = hashOps.entries(RabbitConstant.MESSAGE_CONSUMER_CONFIRM);
            if(entries.containsKey(msgId)){
                //消息已经被消费了
                log.error("消息已经被消费了=====》{}",msgId);
                channel.basicAck(tagId,false);
                return;
            }

//            log.error("出现了异常");
//            int a = 1/0;//模拟异常
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            //发件人
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            //收件人
            mimeMessageHelper.setTo(employee.getEmail());
            //主题
            mimeMessageHelper.setSubject("欢迎新员工");
            //发送时间
            mimeMessageHelper.setSentDate(new Date());
            //邮件内容
            Context context = new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("gender",employee.getGender());
            context.setVariable("departmentId",employee.getDepartmentId());
            context.setVariable("jobLevelId",employee.getJobLevelId());
            String welcomeNewEmp = templateEngine.process("welcomeNewEmp", context);
            mimeMessageHelper.setText(welcomeNewEmp,true);
            //发送邮件
            javaMailSender.send(mimeMessage);
            log.info("发送邮件成功");
            //记录消息已经消费了
            hashOps.put(RabbitConstant.MESSAGE_CONSUMER_CONFIRM,msgId,"OK");
            channel.basicAck(tagId,false);
        } catch (Exception e) {
            log.error("抓住了异常,消息ID:{},详情====》{}",msgId,e);
            channel.basicNack(tagId,false,false);
        }
    }
}
