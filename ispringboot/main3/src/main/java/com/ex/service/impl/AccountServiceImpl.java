package com.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.entity.Account;
import com.ex.mapper.AccountMapper;
import com.ex.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-04-05
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    public LambdaQueryWrapper<Account> getLqw(){
        return Wrappers.lambdaQuery(Account.class);
    }
    public LambdaUpdateWrapper<Account> getLuw(){
        return Wrappers.lambdaUpdate(Account.class);
    }
    @Override
    @Transactional
    public boolean transfer(int rid, int tid, Long money) {
        Account r = getOne(getLqw().select(Account::getBalance).eq(Account::getId, rid));
        Account t = getOne(getLqw().select(Account::getBalance).eq(Account::getId, tid));
        if(r==null || t==null || r.getBalance()==null || t.getBalance()==null){
            throw new RuntimeException("账户不存在或者余额为空");
        }
        if(r.getBalance()<money){
            throw new RuntimeException("余额不足");
        }
        boolean flag1 = update(getLuw().set(Account::getBalance, r.getBalance() - money).eq(Account::getId, rid));
        boolean flag2 = update(getLuw().set(Account::getBalance, t.getBalance() + money).eq(Account::getId, tid));

        return flag1&&flag2;
    }
}
