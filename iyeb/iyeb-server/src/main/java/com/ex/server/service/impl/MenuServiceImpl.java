package com.ex.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.server.entity.Admin;
import com.ex.server.entity.Menu;
import com.ex.server.exception.BusinessException;
import com.ex.server.mapper.MenuMapper;
import com.ex.server.service.IBaseService;
import com.ex.server.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.server.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new BusinessException("Admin为空，用户未登录");
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
    public Menu getAllMenu() {
        List<Menu> menus = list(
                Wrappers.lambdaQuery(Menu.class)
                        .select(s -> "id".equals(s.getProperty()) ||
                                "parentId".equals(s.getProperty()) ||
                                "path".equals(s.getProperty()) ||
                                "name".equals(s.getProperty())
                        ));
        return findChild(menus, menus.get(0));
    }

    /**
     * 递归获取所有子菜单
     * @param menus
     * @param parentMenu
     * @return
     */
    public Menu findChild(List<Menu> menus,Menu parentMenu){
        for (Menu menu : menus) {
            //匹配子节点
            if (menu.getParentId().equals(parentMenu.getId())) {
                //将子节点添加到父节点中
                if (parentMenu.getChildren()==null) {
                    parentMenu.setChildren(new ArrayList<>());
                }
                parentMenu.getChildren().add(menu);
                //子节点查找自己的子节点
                findChild(menus,menu);
            }
        }
        return parentMenu;
    }

    @Override
    public Class<Menu> buildEntity() {
        return Menu.class;
    }
}
