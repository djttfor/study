package com.ex.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ex.entity.User;
import com.ex.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Test1 {
    Logger log = LoggerFactory.getLogger(Test1.class);
    @Autowired
    UserMapper userMapper;

    @Test
    public void test(){
        Page<User> page = new Page<>(1,5);
        Page<User> userPage = userMapper.selectPage(page, null);
        List<User> records = userPage.getRecords();
        for (User record : records) {
            System.out.println(record);
        }

    }
}
