package com.ex.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ex.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user")
    List<User> findAll();
}
