package com.ex.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ex.server.mapper")
public class IYebServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IYebServerApplication.class,args);
    }
}
