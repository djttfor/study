package com.ex.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class Test1 {
    @Test
    public void test1(){
        try {
            throw new RuntimeException();
        }catch (Exception e){
            log.error("出现了异常：",e);
        }
    }
}
