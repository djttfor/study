package com.ex.test;

import com.ex.cache.VisitService;
import com.ex.entity.Account;
import com.ex.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@SpringBootTest
public class Test1 {
    @Autowired
    AccountService accountService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    VisitService visitService;

    @Test
    public void test1(){
        PasswordEncoder pw = new BCryptPasswordEncoder();
        String encode = pw.encode("123");
        log.info("加密后：{}",encode);
        boolean matches = pw.matches("123", encode);
        log.info("是否匹配：{}",matches?"是":"否");
    }

    @Test
    public void test2(){
        List<Account> list = accountService.list();
        for (Account account : list) {
            System.out.println(account);
        }
    }
    @Test
    public void test3(){
        List<Account> accounts = accountService.queryAll();
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    @Test
    public void test4(){
        System.out.println(visitService.getVisitCount());
        visitService.incrementVisitCount("ssss","bbbb");
        System.out.println(visitService.getVisitCount());
    }

}
