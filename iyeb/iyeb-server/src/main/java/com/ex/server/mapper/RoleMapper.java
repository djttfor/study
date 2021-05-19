package com.ex.server.mapper;

import com.ex.server.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
public interface RoleMapper extends BaseMapper<Role> {
    Role getRoleByAdminId(int aid);

    List<Integer> getMenuIdByRid(@Param("rid") Integer rid);
}
