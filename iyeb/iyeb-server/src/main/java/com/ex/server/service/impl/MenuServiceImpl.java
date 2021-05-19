package com.ex.server.service.impl;

import com.ex.server.entity.Admin;
import com.ex.server.entity.Menu;
import com.ex.server.exception.MyException;
import com.ex.server.mapper.MenuMapper;
import com.ex.server.service.IBaseService;
import com.ex.server.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.server.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService, IBaseService<Menu> {

    @Autowired
    MenuMapper menuMapper;

    /**
     * 早期废案，不用理会
     * @return
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        Admin admin = CommonUtil.getPrincipal();
        if(null == admin){
            throw new MyException("Admin为空，用户未登录");
        }
        return menuMapper.getMenuByAdminId(admin.getId());
    }

    @Override
    public List<Menu> getMenuByRoleId(int id) {
        return menuMapper.getMenuByRoleId(id);
    }

    @Override
    public List<Menu> getMenuSorted() {
        return menuMapper.getMenuSorted();
    }

    @Override
    public Class<Menu> buildEntity() {
        return Menu.class;
    }
}
