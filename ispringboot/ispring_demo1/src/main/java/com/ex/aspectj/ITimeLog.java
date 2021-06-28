package com.ex.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Aspect
@Component
@Slf4j
public class ITimeLog {
    private Long startTime ;
    private Long endTime;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

   // @Pointcut("execution(* com.ex.service.*.*(..))")
    @Pointcut("@annotation(com.ex.anno.IMarker)")
    public void logExpresion(){}

    @Before("logExpresion()")
    public void before(){
        startTime = System.currentTimeMillis();
        log.info("startTime:{}",sf.format(new Date(startTime)));
    }
    @After("logExpresion()")
    public void after(){
        endTime = System.currentTimeMillis();
        log.info("endTime:{}",sf.format(new Date(endTime)));
        log.info("用时：{}ms",endTime - startTime);
    }
}
