package com.ex.test;


import com.ex.config.MySpringConfig;
import com.ex.domain.Account;
import com.ex.domain.User;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest1 {
    @Test
    public void test1(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MySpringConfig.class);
        Account account = (Account) ac.getBean("user");
        System.out.println(account);
    }
    @Test
    public void test2(){
        A a = () -> System.out.println("heheh");
        a.a();
    }
    @Test
    public void test3(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MySpringConfig.class);
        User user = (User) ac.getBean("user");
        System.out.println(user);
    }
    @Test
    public void test4(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MySpringConfig.class);
        Account account = (Account) ac.getBean("account");
        System.out.println(account);
    }
    @Test
    public void test5(){

    }
}
//@FunctionalInterface
interface A{
    public void a();
    default void a2(){
        System.out.println("这是接口A默认方法");
    }
}
