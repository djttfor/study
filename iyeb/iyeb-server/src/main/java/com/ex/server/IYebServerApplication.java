package com.ex.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
public class IYebServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IYebServerApplication.class,args);
    }
}
