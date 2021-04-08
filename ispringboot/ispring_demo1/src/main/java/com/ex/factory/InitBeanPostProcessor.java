package com.ex.factory;

import com.ex.aspectj.ITimeLog;
import com.ex.entity.Account;
import com.ex.service.AccountService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

@Slf4j
@Component
public class InitBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AccountService) {
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), (proxy, method, args) -> {
                ITimeLog iTimeLog = new ITimeLog();
                iTimeLog.before();
                Object result = method.invoke(bean, args);
                iTimeLog.after();
                return result;
            });
        }
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("account".equals(beanName)) {
            log.info("初始化前");
        }
        return null;
    }
    //实例化前，如果直接返回Object对象那么Spring会把它当成bean保存，不会进行接下来的生命周期步骤，如果返回null，那么继续生命周期
    @SneakyThrows
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
        if (Account.class.isAssignableFrom(beanClass)){
            log.info("实例化前");
            return beanClass.getConstructor().newInstance();
        }
        return null;
    }
    //实例化后，返回false停止生命周期，返回true继续生命周期
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(bean instanceof Account){
            log.info("实例化后");
            return false;
        }
        return true;
    }
}
