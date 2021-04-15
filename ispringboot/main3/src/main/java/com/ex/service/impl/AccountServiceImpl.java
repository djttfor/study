package com.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ex.entity.Account;
import com.ex.mapper.AccountMapper;
import com.ex.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ex.constant.RedisConstant.ALL_ACCOUNT;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-04-05
 */
@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

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

    @Override
    public List<Account> queryAll() {
        Object o = redisTemplate.opsForValue().get(ALL_ACCOUNT);
        if(null != o){
            log.info("从redis中查询");
            return  (List<Account>) o;
        }
        log.info("从数据库中查询");
        List<Account> list = list();
        if(null != list){
            log.info("存入redis中");
            redisTemplate.opsForValue().set(ALL_ACCOUNT,list);
        }
        return list;
    }
}
