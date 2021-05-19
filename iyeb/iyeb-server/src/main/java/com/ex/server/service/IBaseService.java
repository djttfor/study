package com.ex.server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * 生成wrapper条件类
 * @param <T>
 */
public interface IBaseService<T> {

    Class<T> buildEntity();

    default LambdaQueryWrapper<T> lqw(){
        return new LambdaQueryWrapper<>(buildEntity());
    }

    default QueryWrapper<T> qw(){
        return Wrappers.query();
    }

    default LambdaUpdateWrapper<T> luw(){
        return Wrappers.lambdaUpdate(buildEntity());
    }
    default UpdateWrapper<T> uw(){
        return Wrappers.update();
    }
}
