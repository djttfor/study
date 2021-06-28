package com.ex.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IYebServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IYebServerApplication.class,args);
    }
}
