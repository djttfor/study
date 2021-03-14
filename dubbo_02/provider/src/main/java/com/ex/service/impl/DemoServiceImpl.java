package com.ex.service.impl;

import com.ex.service.DemoService;

public class DemoServiceImpl implements DemoService {
    public void SayHello() {
        System.out.println("hello dubbo!");
    }

    public String SayHello2() {
        return "hello consumer";
    }
}
