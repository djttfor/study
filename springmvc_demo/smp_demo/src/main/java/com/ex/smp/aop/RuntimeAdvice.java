package com.ex.smp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
public class RuntimeAdvice {
    private static final Logger log = LoggerFactory.getLogger(RuntimeAdvice.class);

    @Autowired
    private SelfTransactionManager selfTransactionManager;

    @Pointcut("execution(* com.ex.smp.service.*.*.*(..))")
    public void runtime(){}

    @Around("runtime()")
    public Object runtimeAdvice(ProceedingJoinPoint pjp){
        try {
            long startTime = System.currentTimeMillis();
            log.info("开始时间为:{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
            Object result = pjp.proceed(pjp.getArgs());
            long endTime = System.currentTimeMillis();
            log.info("程序运行时间为:{}ms",endTime-startTime);
            log.info("开始时间为:{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException(throwable);
        }
    }
    @Around("execution(* com.ex.smp.service.*.*.transfer*(..))")
    public Object transaction(ProceedingJoinPoint joinPoint){
        TransactionStatus begin = null;
        try {
            begin = selfTransactionManager.begin();
            log.info("事务启动");
            Object result = joinPoint.proceed(joinPoint.getArgs());
            selfTransactionManager.commit(begin);
            log.info("事务提交");
            return result;
        } catch (Throwable throwable) {
            selfTransactionManager.rollback(begin);
            log.info("出现了异常,事务回滚");
            throw new RuntimeException(throwable);
        }
    }
}
