package com.ex.test;

import com.ex.config.ISpringConfig;
import com.ex.mapper.UserMapper;
import com.ex.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Demo1 {
    private static final Logger logger = LoggerFactory.getLogger(Demo1.class);
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext an =
                new AnnotationConfigApplicationContext(ISpringConfig.class);
        UserMapper userMapper = (UserMapper) an.getBean("userMapper");
        List<User> users = userMapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }
}

