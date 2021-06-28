package com.ex.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/a")
    public String a(){
        return "aaa";
    }
    @RequestMapping("/b")
    public String b(){
        return "bbb";
    }
    @RequestMapping("/c")
    public String c(){
        return "ccc";
    }
}
