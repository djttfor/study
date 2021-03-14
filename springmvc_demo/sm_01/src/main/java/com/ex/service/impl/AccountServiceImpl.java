package com.ex.service.impl;

import com.ex.mapper.AccountMapper;
import com.ex.pojo.Account;
import com.ex.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;
    @Override
   @Transactional
    public Boolean transfer(Integer resourceId, Integer targetId, Long money) {
        Account resourceAccount = accountMapper.findAccountByAid(resourceId);
        if (null==resourceAccount){
            throw new RuntimeException("账户"+resourceId+"不存在");
        }
        Long resourceAccountBalance = resourceAccount.getBalance();
        if(resourceAccountBalance<money){
            throw new RuntimeException("余额不足");
        }
        Account targetAccount = accountMapper.findAccountByAid(targetId);
        if(null==targetAccount){
            throw new RuntimeException("账户"+resourceId+"不存在");
        }
        Long targetAccountBalance = targetAccount.getBalance();
        return accountMapper.updateAccountBalance(resourceId, resourceAccountBalance - money)>0 &&
                accountMapper.updateAccountBalance(targetId,targetAccountBalance + money)>0;
    }
}
