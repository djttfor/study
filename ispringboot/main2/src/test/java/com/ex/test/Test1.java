package com.ex.test;

import com.ex.entity.User;
import com.ex.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Test1 {
    @Autowired
    UserMapper userMapper;

    @Test
    public void test1(){
        PageHelper.startPage(1,5);
        Page<User> page = (Page<User>) userMapper.queryAll();
        List<User> result = page.getResult();
        for (User user : result) {
            System.out.println(user);
        }
    }
}
