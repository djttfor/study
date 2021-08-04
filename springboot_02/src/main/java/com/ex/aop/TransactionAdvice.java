package com.ex.aop;

import com.ex.transaction.SelfTransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

@Component
@Slf4j
@Aspect
public class TransactionAdvice {
    @Autowired
    SelfTransactionManager transactionManager;

    @Pointcut("@annotation(com.ex.annotation.SelfTransactional)")
    public void expression(){}

    @Around("expression()")
    public Object advice(ProceedingJoinPoint pjp){
        try {
            TransactionStatus status = transactionManager.begin();
            log.info("开启事务");

            Object proceed = pjp.proceed();

            transactionManager.commit(status);
            log.info("提交事务");
            return proceed;
        } catch (Throwable throwable) {
            transactionManager.rollBack();
            log.error("出现了异常，回滚事务",throwable);
            throw new RuntimeException(throwable);
        }
    }
}
