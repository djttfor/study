package com.ex.ssm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ex.ssm.entity.User;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-03-11
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> selectAll();
}
