package com.ex.test;

import com.ex.entity.User;
import com.ex.mapper.UserMapper;
import com.ex.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test1 {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    public void test1(){
       User user = userService.selectByName("ROOT");
        System.out.println(user);
    }
}
class A{
    private static final ThreadLocal<Integer> it = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            it.set(10086);
        });
        thread.start();
        thread.join();
        System.out.println(it.get());
    }
}
