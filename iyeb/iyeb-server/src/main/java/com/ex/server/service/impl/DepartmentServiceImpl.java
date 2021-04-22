package com.ex.server.service.impl;

import com.ex.server.entity.Department;
import com.ex.server.mapper.DepartmentMapper;
import com.ex.server.service.DepartmentService;
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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}
