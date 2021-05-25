package com.ex.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.server.dto.RoleBean;
import com.ex.server.entity.MenuRole;
import com.ex.server.entity.Role;
import com.ex.server.mapper.RoleMapper;
import com.ex.server.service.AdminRoleService;
import com.ex.server.service.AdminService;
import com.ex.server.service.MenuRoleService;
import com.ex.server.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    MenuRoleService menuRoleService;

    @Autowired
    AdminRoleService adminRoleService;

    @Override
    public Role getRoleByAdminId(int aid) {
        return roleMapper.getRoleByAdminId(aid);
    }

    @Override
    public List<Integer> getMenuIdByRid(Integer rid) {
        return roleMapper.getMenuIdByRid(rid);
    }

    @Override
    @Transactional
    public boolean addRole(RoleBean roleBean) {
        if(roleBean.getName()==null||roleBean.getCode()==null||roleBean.getRemark()==null){
            return false;
        }
        //保存角色
        Role role = new Role();
        role.setName(roleBean.getName());
        role.setCode(roleBean.getCode());
        role.setRemark(roleBean.getRemark());
        role.setGmtCreate(LocalDateTime.now());
        role.setEnabled(true);
        save(role);

        //保存权限
        List<Integer> addArr = roleBean.getAddArr();
        if (addArr!=null || addArr.size()>0) {
            List<MenuRole> list = new ArrayList<>();
            for (Integer mid : addArr) {
                MenuRole menuRole = new MenuRole();
                menuRole.setRid(role.getId());
                menuRole.setMid(mid);
                menuRole.setGmtCreate(LocalDateTime.now());
                menuRole.setRemark(LocalDateTime.now()+"添加");
                list.add(menuRole);
            }
            menuRoleService.saveBatch(list);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteById(Integer id) {
        //删除role表的记录
        removeById(id);
        //删除role_menu表的记录
        menuRoleService.remove(Wrappers.lambdaUpdate(MenuRole.class).eq(MenuRole::getRid,id));
        return true;
    }
}
