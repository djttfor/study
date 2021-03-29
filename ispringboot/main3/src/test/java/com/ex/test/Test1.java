package com.ex.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootTest
public class Test1 {
    @Test
    public void test1(){
        PasswordEncoder pw = new BCryptPasswordEncoder();
        String encode = pw.encode("123");
        log.info("加密后：{}",encode);
        boolean matches = pw.matches("123", encode);
        log.info("是否匹配：{}",matches?"是":"否");
    }
}
