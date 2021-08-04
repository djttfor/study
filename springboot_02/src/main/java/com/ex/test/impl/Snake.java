package com.ex.test.impl;

import com.ex.test.Animal;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class Snake implements Animal {
    @Override
    public void showName() {
        System.out.println("你就是条🐍");
    }

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E:\\Java\\springboot_02\\cgclass");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Bird.class);
        enhancer.setCallback(new MyMethodInterceptor());
        Bird bird = (Bird) enhancer.create();
        bird.showName();

    }

    static class MyMethodInterceptor implements MethodInterceptor{
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("前置增强");
            Object result = methodProxy.invokeSuper(o,objects);
            System.out.println("后置增强");
            return result;
        }
    }
}
