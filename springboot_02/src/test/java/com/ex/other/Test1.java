package com.ex.other;

import com.ex.Springboot02Application;
import com.ex.domain.Hehe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Springboot02Application.class)
public class Test1 {

    @Autowired
    Hehe h;
    @Test
    public void test1(){
        System.out.println(h);
    }
}
