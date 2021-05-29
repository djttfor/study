package com.ex.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ex.server.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
public interface EmployeeService extends IService<Employee> {

    Page<Employee> pageQuery(@Param("current") int current, @Param("pageSize") int pageSize);
}
