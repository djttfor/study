package com.ex.server.mapper;

import com.ex.server.entity.Menu;
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
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenuByAdminId(Integer adminId);

    List<Menu> getMenuByRoleId(@Param("rid") int id);

    List<Menu> getMenuSorted();
}
