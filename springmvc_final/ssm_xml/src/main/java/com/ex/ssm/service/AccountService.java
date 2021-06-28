package com.ex.ssm.service;

import com.ex.ssm.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-03-11
 */
public interface AccountService extends IService<Account> {
    void tranfer(Integer resId,Integer targetId,Long balance);
}
