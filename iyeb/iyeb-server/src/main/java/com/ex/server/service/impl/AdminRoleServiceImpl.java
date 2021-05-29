package com.ex.server.service.impl;

import com.ex.server.entity.AdminRole;
import com.ex.server.mapper.AdminRoleMapper;
import com.ex.server.service.AdminRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {

    @Autowired
    AdminRoleMapper adminRoleMapper;

    @Override
    public AdminRole test1() {
        return adminRoleMapper.test1();
    }
}
