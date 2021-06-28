package com.ex.mapper;

import com.ex.Springboot02Application;
import com.ex.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Springboot02Application.class)
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void findAll(){
        List<User> users = userMapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

}
