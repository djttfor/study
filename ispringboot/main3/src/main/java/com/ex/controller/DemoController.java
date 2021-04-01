package com.ex.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/demo")
@Controller
public class DemoController {
    @RequestMapping("/t1")
    public String toT1(){
        return "/demo/t1";
    }
}
