package com.ex.server.service.impl;

import com.ex.server.entity.Employee;
import com.ex.server.mapper.EmployeeMapper;
import com.ex.server.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
