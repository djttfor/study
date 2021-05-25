package com.ex.server.mapper;

import com.ex.server.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> selectAll();
}
