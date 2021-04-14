package com.ex.service;

import com.ex.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-04-05
 */
public interface AccountService extends IService<Account> {
    boolean transfer(int rid,int tid,Long money);
}
