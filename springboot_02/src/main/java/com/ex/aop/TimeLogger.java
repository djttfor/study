package com.ex.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Slf4j
@Aspect
@Component
public class TimeLogger {

    public static final ThreadLocal<DateFormat> FORMAT_THREAD_LOCAL =
            ThreadLocal.withInitial(()->new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")) ;

    @Pointcut("execution(* com.ex.service.*.*(..))")
    public void expression(){}

    @Around("expression()")
    public Object log(ProceedingJoinPoint pjp) {
        try {
            long start = System.currentTimeMillis();
            //log.info("开始时间：{}",FORMAT_THREAD_LOCAL.get().format(new Date(start)));

            Object result = pjp.proceed(pjp.getArgs());

            long end = System.currentTimeMillis();
            //log.info("结束时间：{}",FORMAT_THREAD_LOCAL.get().format(new Date(end)));
            log.info("执行时间：{}ms",end - start);

            return result;
        } catch (Throwable throwable) {
            log.error("出现了异常：",throwable);
            throw new RuntimeException(throwable);
        }finally {
            //log.info("最终通知");
        }
    }

}
