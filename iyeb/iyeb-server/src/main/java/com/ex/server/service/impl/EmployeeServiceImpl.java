package com.ex.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.server.entity.Employee;
import com.ex.server.mapper.EmployeeMapper;
import com.ex.server.rabbit.producers.MailProducers;
import com.ex.server.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    MailProducers mailProducers;

    @Override
    public Page<Employee> pageQuery(int current, int pageSize) {
        Page<Employee> page = new Page<>(current,pageSize);
        return page(page);
    }

    @Override
    public boolean addEmployee(Employee employee) {
        //把发送邮件的消息放到消息队列
        mailProducers.sendWelcomeMail(employee);
        return true;
    }
}
