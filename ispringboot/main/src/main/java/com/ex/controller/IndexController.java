package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String toMain(){
        return "forward:/index.html";
    }
    @GetMapping("/tIndex")
    public String toThyIndex(){
        return "index.html";
    }
}
