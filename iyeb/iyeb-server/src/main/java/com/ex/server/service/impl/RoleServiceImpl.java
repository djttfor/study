package com.ex.server.service.impl;

import com.ex.server.entity.Role;
import com.ex.server.mapper.RoleMapper;
import com.ex.server.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Role getRoleByAdminId(int aid) {
        return roleMapper.getRoleByAdminId(aid);
    }

    @Override
    public List<Integer> getMenuIdByRid(Integer rid) {
        return roleMapper.getMenuIdByRid(rid);
    }
}
