package com.ex.ssm.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class MyTimeAdvice {
    private static final Logger log = LoggerFactory.getLogger(MyTimeAdvice.class);
    private Long startTime;

    @Pointcut("execution(* com.ex.ssm.mapper.*.*(..))")
    public void expression(){}

    @Before("expression()")
    public void before(){
        log.info("开始,当前时间为{}ms",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        startTime = System.currentTimeMillis();
    }
    @After("expression()")
    public void after(){
        log.info("结束,当前时间为{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        Long endTime = System.currentTimeMillis();
        log.info("总执行时间为{}ms", endTime -startTime);
    }
    @AfterReturning("expression()")
    public void end(){
        log.info("最终,当前时间为{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    }

    @AfterThrowing("expression()")
    public void throwing(){
        log.info("异常,当前时间为{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    }


    public Object around(ProceedingJoinPoint point){
        Object[] args = point.getArgs();
        try {
            log.info("前");
            Object result = point.proceed(args);
            log.info("后");
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.info("异常");
            throw new RuntimeException(throwable);
        }finally {
            log.info("最终");
        }
    }
}
