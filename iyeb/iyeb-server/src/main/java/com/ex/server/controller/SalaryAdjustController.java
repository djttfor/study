package com.ex.server.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

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
public class SalaryAdjustController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/test1")
    public String test1(@RequestParam("name")String name){
        System.out.println("##########################"+name);
        return "test";
    }
    @PostMapping("/test2")
    public String test2(@RequestParam("name")String name){
        System.out.println("##########################"+name);
        return "test";
    }
}

