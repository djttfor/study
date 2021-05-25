package com.ex.server.service;

import com.ex.server.dto.RoleBean;
import com.ex.server.entity.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
public interface MenuRoleService extends IService<MenuRole> {

    boolean setMenuRoleId(@Param("roleBean") RoleBean roleBean);
}
