package com.ex.ssm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.ssm.entity.User;
import com.ex.ssm.mapper.UserMapper;
import com.ex.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-02-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    public LambdaQueryWrapper<User> getQueryWrapper(){
        return Wrappers.lambdaQuery(User.class);
    }

    public Page<User> getPage(int current,int pageSize){
        return new Page<>(current,pageSize);
    }
    @Override
    public List<User> queryAllByNestedResult() {
        return userMapper.queryAllByNestedResult();
    }

    @Override
    public User loginCheck(User user){
        return userMapper.selectOne(getQueryWrapper()
                .eq(User::getUsername,user.getUsername())
                .eq(User::getPassword,user.getPassword()));
    }

    @Override
    public Page<User> selectPage(int current, int pageSize) {
        return page(getPage(current, pageSize));
    }

    @Override
    public Page<User> selectLikePage(User user, int current, int pageSize) {
        return page(getPage(current, pageSize),
                getQueryWrapper()
                        .like(User::getUsername,user.getUsername()));

    }
}