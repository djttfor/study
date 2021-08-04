package com.ex.controller;

import com.ex.entity.User;
import com.ex.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class HelloWorldController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/hello")
    public List<User> hello(){

        return userMapper.selectList(null);
    }
}
