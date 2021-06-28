package com.ex.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ex.entity.Account;

import java.util.List;

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

    List<Account> queryAll();
}
