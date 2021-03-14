package com.ex.smp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.smp.entity.Account;
import com.ex.smp.mapper.AccountMapper;
import com.ex.smp.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ttfor
 * @since 2021-01-20
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Autowired
    AccountMapper accountMapper;
    @Override
    @Transactional
    public Boolean transfer2(Integer resourceId, Integer targetId, Long balance) {
        Boolean result = false;
        Long aLong = queryBalance2(resourceId);
        if(aLong<balance){
            throw new RuntimeException("账户"+resourceId+"余额不足");
        }
        Long aLong1 = queryBalance2(targetId);
        result = updateBalance2(resourceId,aLong-balance);
        if(!result){
            throw new RuntimeException(Thread.currentThread().getName()+"更新账户"+resourceId+"失败#####################################################");
        }else{
            System.out.println(Thread.currentThread().getName()+"更新账户"+resourceId+"成功#####################################################");
        }
        //int a=1/0;
        result = updateBalance2(targetId,aLong1+balance);
        if(!result){
            throw new RuntimeException(Thread.currentThread().getName()+"更新账户"+targetId+"失败#####################################################");
        }else{
            System.out.println(Thread.currentThread().getName()+"更新账户"+targetId+"成功#####################################################");
        }
        return result;
    }

    @Override
    public Boolean transfer(Integer rId, Integer tId, Long balance) {
        Long resourceBalance = getBalance(rId);
        if(resourceBalance<balance){
            throw new RuntimeException("账户"+rId+"余额不足");
        }
        Long targetBalance = getBalance(tId);
        Boolean result ;
        updateBalance(rId,resourceBalance-balance);
        //int a = 1/0;
        result = updateBalance(tId, targetBalance + balance);
        return result;
    }
    public Long getBalance(Integer accountId){
        Account rAccount = accountMapper.selectOne(Wrappers.lambdaQuery(new Account()).eq(Account::getId, accountId));
        if(rAccount==null){
            throw new RuntimeException("账户"+accountId+"不存在");
        }else{
            return rAccount.getBalance();
        }
    }

    public Long queryBalance(Integer aid){
        Account account = getOne(Wrappers.lambdaQuery(new Account()).eq(Account::getId, aid));
        if(account==null){
            throw new RuntimeException("账号"+aid+"不存在");
        }else{
            return account.getBalance();
        }
    }
    public Long queryBalance2(Integer aid){
        Account account = accountMapper.selectOne(Wrappers.lambdaQuery(Account.class).eq(Account::getId,aid));
        if(account==null){
            throw new RuntimeException("账号"+aid+"不存在");
        }else{
            return account.getBalance();
        }
    }
    public Boolean updateBalance(Integer aid,Long oldBalance,Long newBalance){
        return update(Wrappers.lambdaUpdate(new Account())
                .set(Account::getBalance, newBalance)
                .eq(Account::getId, aid)
                .eq(Account::getBalance,oldBalance));
    }
    public Boolean updateBalance2(Integer aid,Long oldBalance,Long newBalance){
        return accountMapper.update(new Account(),Wrappers.lambdaUpdate(Account.class)
                .set(Account::getBalance, newBalance)
                .eq(Account::getId, aid)
                .eq(Account::getBalance,oldBalance))>0;
    }
    public Boolean updateBalance(Integer aid,Long newBalance){
        return update(Wrappers.lambdaUpdate(new Account())
                .set(Account::getBalance, newBalance)
                .eq(Account::getId, aid));
    }
    public Boolean updateBalance2(Integer aid,Long newBalance){
        return accountMapper.update(new Account(),Wrappers.lambdaUpdate(Account.class)
                .set(Account::getBalance, newBalance)
                .eq(Account::getId, aid))>0;
    }
}
