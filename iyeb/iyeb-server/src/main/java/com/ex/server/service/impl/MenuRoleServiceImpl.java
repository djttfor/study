package com.ex.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.server.dto.RoleBean;
import com.ex.server.entity.Menu;
import com.ex.server.entity.MenuRole;
import com.ex.server.entity.Role;
import com.ex.server.mapper.MenuRoleMapper;
import com.ex.server.service.MenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.server.service.MenuService;
import com.ex.server.service.RoleService;
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
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {

    @Autowired
    RoleService roleService;

    @Override
    @Transactional
    public boolean setMenuRoleId(RoleBean roleBean) {
        Integer rid = roleBean.getRid();
        if (rid==null){
            return false;
        }
        //保存新的权限
        List<Integer> addArr = roleBean.getAddArr();
        if (addArr!=null || addArr.size()>0) {
            List<MenuRole> list = new ArrayList<>();
            for (Integer mid : addArr) {
                MenuRole menuRole = new MenuRole();
                menuRole.setRid(rid);
                menuRole.setMid(mid);
                menuRole.setGmtCreate(LocalDateTime.now());
                menuRole.setRemark(LocalDateTime.now()+"添加");
                list.add(menuRole);
            }
            saveBatch(list);
        }
        //删除对应的路径
        List<Integer> deleteArr = roleBean.getDeleteArr();
        if (deleteArr!=null || deleteArr.size()>0) {
            for (Integer mid : deleteArr) {
                remove(Wrappers.lambdaUpdate(MenuRole.class).eq(MenuRole::getMid,mid).eq(MenuRole::getRid,rid));
            }
        }
        //更新角色
        Role role = new Role();
        role.setId(rid);
        role.setCode(roleBean.getCode());
        role.setRemark(roleBean.getRemark());
        role.setName(roleBean.getName());
        roleService.updateById(role);
        return true;
    }
}
