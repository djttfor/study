package com.ex.service;

public interface AccountService {
    Boolean transfer(Integer resourceId,Integer targetId,Long money);
}
