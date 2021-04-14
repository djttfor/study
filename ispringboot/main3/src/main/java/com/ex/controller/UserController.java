package com.ex.controller;

import com.ex.entity.Account;
import com.ex.entity.Message;
import com.ex.entity.User;
import com.ex.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    AccountService accountService;


    @PreAuthorize("hasRole('v2')")
    @RequestMapping("/l1/{l}")
    public String l1(@PathVariable("l")String l){
        log.info("当前访问的是：{}","L1/"+l);
        return "L1/"+l;
    }
    @RequestMapping("/l2/{l}")
    public String l2(@PathVariable("l")String l){
        log.info("当前访问的是：{}","L1/"+l);
        return "L1/"+l;
    }
    @RequestMapping("/l3/{l}")
    public String l3(@PathVariable("l")String l){
        log.info("当前访问的是：{}","L1/"+l);
        return "L1/"+l;
    }
    @RequestMapping("/test")
    public String toTest(){
        return "login";
    }

    @RequestMapping("/login/toPage")
    public String toPage(){
        return "login";
    }

    @RequestMapping("/login/page")
    @ResponseBody
    public Message toLogin(HttpServletResponse response) throws IOException {
        Message message = new Message();
        message.setCode(1);
        message.setMessage("您未登录，请先登录");
        return message;
    }
//    @RequestMapping("/login/page")
//    public String toLogin(HttpServletResponse response) throws IOException {
//        return "login";
//    }

    @RequestMapping("/login/fail")
    public String toLoginFail(){
        return "loginFail";
    }

    @RequestMapping("/test2")
    @ResponseBody
    public Message test2(@RequestBody User user){
        log.info("前台收到的信息:{}",user);
        return new Message();
    }
    @RequestMapping("/all")
    @ResponseBody
    public List<Account> all(){
        return accountService.list();
    }

}

