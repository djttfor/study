package com.ex.ssm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ex.ssm.entity.Account;
import com.ex.ssm.mapper.AccountMapper;
import com.ex.ssm.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-03-11
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    public LambdaQueryWrapper<Account> getLqw(){
        return Wrappers.lambdaQuery(Account.class);
    }
    public QueryWrapper<Account> getQw(){
        return Wrappers.query(new Account());
    }
    public LambdaUpdateWrapper<Account> getULqw(){
        return Wrappers.lambdaUpdate(Account.class);
    }
    public Long getBalance(Integer id){
        LambdaQueryWrapper<Account> select = getLqw().select(i->i.getProperty().equals("balance")).eq(Account::getId,id);
        Account one = getOne(select);
        return one.getBalance();
    }
    @Override
    @Transactional
    public void tranfer(Integer resId, Integer targetId, Long balance) {
        Long balance1 = getBalance(resId);
        Long balance2 = getBalance(targetId);
        if(balance1<balance){
            throw new RuntimeException(resId+"账户余额不足");
        }
        balance1 = balance1 - balance;
        balance2 = balance2 + balance;
        update(getULqw().set(Account::getBalance, balance1).eq(Account::getId, resId));
        int a = 1/0;
        update(getULqw().set(Account::getBalance, balance2).eq(Account::getId, targetId));
        System.out.println("转账成功");
    }
}
