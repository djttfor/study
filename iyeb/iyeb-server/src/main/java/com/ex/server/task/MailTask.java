package com.ex.server.task;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.server.entity.Employee;
import com.ex.server.entity.MailLog;
import com.ex.server.rabbit.RabbitConstant;
import com.ex.server.rabbit.producers.MailProducers;
import com.ex.server.service.MailLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class MailTask {

    @Autowired
    MailLogService mailLogService;

    @Autowired
    MailProducers mailProducers;

    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void task(){
        log.info("开始扫描发送失败的邮件消息=============>");
        List<MailLog> list = mailLogService.list(Wrappers.lambdaQuery(MailLog.class)
                .lt(MailLog::getCount, RabbitConstant.MAX_RETRY_COUNT)
                .eq(MailLog::getStatus,RabbitConstant.MESSAGE_INITIAL));
        if(list==null || list.size()==0){
            log.info("暂无发送失败的邮件消息");
            return;
        }else{
            log.info("需要重发{}条邮件消息",list.size());
        }
        for (MailLog mailLog : list) {
            String msgId = mailLog.getMsgId();
            //这里要根据Eid查询员工，偷懒直接new了
            Employee employee = new Employee();
            employee.setId(10086);
            employee.setEmail("djttfor@163.com");
            employee.setName("jimmy");
            employee.setGender("女");
            employee.setDepartmentId(2);
            employee.setJobLevelId(1);
            //更新重试次数与重试时间
            mailLogService.update(new MailLog(),Wrappers.lambdaUpdate(MailLog.class).set(MailLog::getCount,mailLog.getCount()+1)
            .set(MailLog::getTryTime, LocalDateTime.now()).eq(MailLog::getMsgId,msgId));
            //重发消息
            mailProducers.resendMailMessage(employee,msgId);
        }
    }
}
