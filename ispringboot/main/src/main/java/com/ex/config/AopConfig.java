package com.ex.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Aspect
@Component
@Slf4j
@EnableTransactionManagement
public class AopConfig {
    @Pointcut("execution(* com..service.*.*(..))")
    public void log1(){}

    @Before("log1()")
    public void before(){
        log.info("before...");
    }
    @After("log1()")
    public void after(){
        log.info("after...");
    }
    @AfterReturning("log1()")
    public void afterReturning(){
        log.info("afterReturning...");
    }
    @AfterThrowing("log1()")
    public void afterThrowing(){
        log.info("afterThrowing...");
    }
}
