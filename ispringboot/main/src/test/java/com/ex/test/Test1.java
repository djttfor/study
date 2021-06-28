package com.ex.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ex.entity.Account;
import com.ex.entity.User;
import com.ex.mapper.UserMapper;
import com.ex.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
@Slf4j
@SpringBootTest
public class Test1 {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    AccountService accountService;


    @Test
    public void test1(){
        Page<User> objectPage = new Page<>(1, 10);
        Page<User> res = userMapper.selectPage(objectPage,new LambdaQueryWrapper<>(User.class)
                .likeRight(User::getPassword,"12"));

        List<User> records = res.getRecords();
        for (User record : records) {
            System.out.println(record);
        }

    }
    @Test
    public void test2() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s1 = (String) redisTemplate.opsForValue().get("user");
        User user1 = objectMapper.readValue(s1, User.class);
        log.error("####################################################################################");
        log.error("user1:{}",user1);
        Object k3 = redisTemplate.opsForValue().get("k3");
        log.error("k3:{}",k3);
        System.out.println(k3);
        log.error("####################################################################################");
    }

    @Test
    public void test3(){
        List<Account> list = accountService.list();
        for (Account account : list) {
            System.out.println(account);
        }
    }

    @Test
    public void test4(){
         System.out.println(accountService.transfer(1,2,1000L));
    }



}
