package com.ex.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ex.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper extends BaseMapper<User> {
    List<User> queryAll();
}
