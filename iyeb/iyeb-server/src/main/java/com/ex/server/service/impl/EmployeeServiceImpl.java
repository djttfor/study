package com.ex.server.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.server.entity.Employee;
import com.ex.server.mapper.EmployeeMapper;
import com.ex.server.service.EmployeeService;
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

    @Override
    public Page<Employee> pageQuery(int current, int pageSize) {
        Page<Employee> page = new Page<>(current,pageSize);
        return page(page);
    }
}
