package com.ex.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
@Slf4j
public class TestController {
    @RequestMapping("/test1")
    public String test1(){
        try {
            throw new RuntimeException();
        }catch (Exception e){
            log.error("出现了异常：",e);
        }
        return "a";
    }
}
