package com.ex.test;

import com.ex.config.ISpringConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@Slf4j
public class Test {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ISpringConfig.class);
        System.out.println(ac.getBean("account"));
    }

}
