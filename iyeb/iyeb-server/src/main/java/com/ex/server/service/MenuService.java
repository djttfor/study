package com.ex.server.service;

import com.ex.server.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
public interface MenuService extends IService<Menu> {

    List<Menu> getMenuByAdminId();

    List<Menu> getMenuByRoleId(int id);

    List<Menu> getMenuSorted();
}
