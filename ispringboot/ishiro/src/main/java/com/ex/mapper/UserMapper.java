package com.ex.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ex.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
