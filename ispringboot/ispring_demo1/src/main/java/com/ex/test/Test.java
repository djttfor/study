package com.ex.test;

import com.ex.config.ISpringConfig;
import com.ex.entity.Account;
import com.ex.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@Slf4j
public class Test {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ISpringConfig.class);
        AccountMapper accountMapper = (AccountMapper) ac.getBean("accountMapper");
        Account account = accountMapper.queryByAid(1);
        System.out.println(account);
    }

}
