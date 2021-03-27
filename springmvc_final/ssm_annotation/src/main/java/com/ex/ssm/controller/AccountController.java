package com.ex.ssm.controller;

import com.ex.ssm.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-02-02
 */
@Controller
@RequestMapping("/account")
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    @GetMapping("/test1")
    public void test1(){
        log.info("这是普通的GET访问");
        return;
    }
    @GetMapping("/test1/{a}/{b}")
    public void test1(@PathVariable(value = "a") int a, @PathVariable(value = "b",required = false) int b){
        log.info("这是Restful风格的访问参数：{},{}",a,b);
    }
    @GetMapping("/testUser")
    @ResponseBody
    public User testUser(){
        User user = new User();
        user.setUsername("jimmy");
        return user;
    }
}
