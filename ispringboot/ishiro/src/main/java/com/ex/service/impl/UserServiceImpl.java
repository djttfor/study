package com.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.entity.User;
import com.ex.mapper.UserMapper;
import com.ex.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public LambdaQueryWrapper<User> getLqw(){
        return Wrappers.lambdaQuery(User.class);
    }
    public QueryWrapper<User> getQw(){
        return Wrappers.query(new User());
    }
    @Override
    public User selectByName(String name) {
        //getOne(getQw().eq("username",name));
        return getOne(getLqw().eq(User::getUsername,name));
    }
}
