package com.ex.smp.service;

import com.ex.smp.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ttfor
 * @since 2021-01-20
 */
public interface IAccountService extends IService<Account> {
    Boolean transfer2(Integer i, Integer i1, Long l);
    Boolean transfer(Integer rId,Integer tId,Long balance);
}
