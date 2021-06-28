package com.ex.ssm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ex.ssm.entity.User;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-03-11
 */
public interface UserService extends IService<User> {
    Page<User> pageQuery(int current,int pageSize);
}
