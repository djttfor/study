package com.ex.redis;

import com.ex.Springboot02Application;
import com.ex.domain.User;
import com.ex.mapper.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Springboot02Application.class)
public class JedisTest {

    @Autowired
    private RedisTemplate<String,String > redisTemplate;
    @Autowired
    UserMapper userMapper;

    @Test
    public void test1() throws JsonProcessingException {
       //redisTemplate.boundValueOps("name").set("may");
        //System.out.println(redisTemplate.boundValueOps("name").get());
//        redisTemplate.boundZSetOps("hehe5").add("a",10);
//        redisTemplate.boundZSetOps("hehe5").add("b",11);
//        redisTemplate.boundZSetOps("hehe5").add("c",12);
//        Set<String> hehe5 = redisTemplate.boundZSetOps("hehe5").range(0, -1);
//        for (String s : hehe5) {
//            System.out.println("分数:"+redisTemplate.boundZSetOps("hehe5").score(s)+"value:"+s);
//        }

//        for (User user : all) {
//            System.out.println(user);
//        }
        String findAll = redisTemplate.boundValueOps("findAll").get();
        if(findAll==null){
            System.out.println("从数据库中查询-------------------------------------------");
            List<User> all = userMapper.findAll();
            ObjectMapper om = new ObjectMapper();
            String s= om.writeValueAsString(all);
            redisTemplate.boundValueOps("findAll").set(s);
            System.out.println("json:"+s);
        }else{
            System.out.println("从redis缓存查询-------------------------------------------");
            System.out.println(findAll);
        }


    }
}
