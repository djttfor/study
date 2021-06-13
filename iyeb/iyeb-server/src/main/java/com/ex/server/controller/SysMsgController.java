package com.ex.server.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 消息状态表 前端控制器
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@Controller
@RequestMapping("/sys/demo")
public class SysMsgController {
    @RequestMapping("/test1")
    public String test1(){
        return "test1";
    }
}

