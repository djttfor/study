package com.ex.ssm.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ex.ssm.entity.User;
import com.ex.ssm.mapper.UserMapper;
import com.ex.ssm.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-03-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public Page<User> pageQuery(int current, int pageSize) {
        return page(new Page<>(current,pageSize));
    }
}
