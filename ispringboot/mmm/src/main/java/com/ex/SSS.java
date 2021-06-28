package com.ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan("com.ex.filter")
@SpringBootApplication
public class SSS {
    public static void main(String[] args) {
        SpringApplication.run(SSS.class,args);
    }
}
