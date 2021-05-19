package com.ex.server.service;

import com.ex.server.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
public interface RoleService extends IService<Role> {

    Role getRoleByAdminId(int aid);

    List<Integer> getMenuIdByRid(@Param("rid") Integer rid);
}
