package com.ex.server.itest;

import com.ex.server.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public static void main(String[] args) {

    }
}
