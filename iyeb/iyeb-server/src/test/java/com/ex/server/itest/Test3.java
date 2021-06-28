package com.ex.server.itest;

import com.ex.server.entity.Admin;
import com.ex.server.util.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.TreeMap;

@SpringBootTest
@Slf4j
public class Test3 {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Test
    public void test1(){
        String token  = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZWQiOjE2MjMzMTY5MjI3MjUsImV4cCI6MTYyMzkyMTcyMn0.uJy7PWsa8fKHENlcUQollAvhTRlWxZeeAtJZGCXMTV8" ;
        Claims claims = jwtTokenUtil.parseToken(token);
        System.out.println(claims);
    }
}
class a{
    public static void main(String[] args) throws JsonProcessingException {
        TreeMap<Admin,Integer> treeMap = new TreeMap<>(Comparator.comparing(Admin::getId));
        Admin a1 = new Admin();
        Admin a2 = new Admin();
        a1.setId(9999999);
        a2.setId(1006611);
        treeMap.put(a1,1);
        treeMap.put(a2,2);
        treeMap.forEach((k,v)->{
            System.out.println(k+","+v);
        });
    }
}
