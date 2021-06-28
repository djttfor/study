package com.ex.ssm.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ex.ssm.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-02-15
 */
public interface AccountMapper extends BaseMapper<Account> {
    Account queryAccountByUid(Integer uid);
    Account queryAccountByMybatisplus(@Param(Constants.WRAPPER)Wrapper<Account> ew);
}