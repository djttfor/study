package com.ex.service.impl;

import com.ex.anno.IMarker;
import com.ex.entity.Account;
import com.ex.mapper.AccountMapper;
import com.ex.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;

    @Override
    @IMarker
    public Account queryByAid(Integer aid) {
        return accountMapper.queryByAid(aid);
    }
}
