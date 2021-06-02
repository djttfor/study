package com.ex.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.server.entity.Employee;
import com.ex.server.entity.MailLog;
import com.ex.server.mapper.MailLogMapper;
import com.ex.server.rabbit.RabbitConstant;
import com.ex.server.service.MailLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements MailLogService {

    @Override
    public boolean saveRedundant(Employee employee,String msgId) {
        MailLog mailLog = new MailLog();
        mailLog.setEId(employee.getId());
        mailLog.setCount(0);
        mailLog.setStatus(0);
        mailLog.setMsgId(msgId);
        mailLog.setExchange(RabbitConstant.MAIL_EX);
        mailLog.setRouteKey(RabbitConstant.MAIL_WELCOME);
        return save(mailLog);
    }
}
