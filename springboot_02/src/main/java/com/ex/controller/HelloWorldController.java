package com.ex.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {
    @Value("${person.name}")
    String name;
    @Value("${person.age}")
    Integer age;
    @Value("${person.addr}")
    String addr;
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "name:"+name+",age:"+age+",addr:"+addr;
    }
}
