package com.ex.server.controller;


import com.ex.server.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * <p>
 * 调薪表 前端控制器
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@Controller
@RequestMapping("/sys/test")
@Slf4j
public class SalaryAdjustController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/test1")
    public String test1(@RequestParam("name")String name,  Authentication authentication, Admin admin){
        Principal principal = (Principal) authentication.getPrincipal();
        log.info("用户名：{}",principal.getName());
        System.out.println("##########################"+name);
        return "test";
    }
    @PostMapping("/test2")
    public String test2(String name, @RequestBody Admin admin){
        System.out.println("##########################"+name);
        log.info("admin:{}",admin);
        return "test";
    }
}

