package com.ex.server.service.impl;

import com.ex.server.entity.Department;
import com.ex.server.mapper.DepartmentMapper;
import com.ex.server.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Department selectAll() {
        List<Department> list = list();
        return findChildren(list,list.get(0));
    }

    public Department findChildren(List<Department> list,Department department){
        for (Department child : list) {
            if (child.getParentId().equals(department.getId())) {
                if(department.getChildren()==null){
                    department.setChildren(new ArrayList<>());
                }
                department.getChildren().add(child);
                findChildren(list,child);
            }
        }
        return department;
    }
}
