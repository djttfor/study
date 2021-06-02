package com.ex.test;

import com.ex.asyn.AsynService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest
public class Test1 {
    @Autowired
    AsynService asynService;

    @Test
    public void test1(){
        long date = System.currentTimeMillis();
        long exp = date + 60 * 1000;
        JwtBuilder jwtBuilder = Jwts.builder()
                //唯一id
                .setId("666")
                //接受的用户
                .setSubject("Vans")
                //签发时间
                .setIssuedAt(new Date())
                //设置失效时间
                .setExpiration(new Date(exp))
                .claim("name","jimmy")
                .claim("ass","hole")
                //签名及密钥
                .signWith(SignatureAlgorithm.HS256,"The deep dark fantasy");

        String token = jwtBuilder.compact();
        log.info("token:{}",token);
        log.info("-------------令牌解析----------------");
        String[] split = token.split("\\.");
        log.info("第一部分：{}", new String(Base64Codec.BASE64.decode(split[0])));
        log.info("第二部分：{}", new String(Base64Codec.BASE64.decode(split[1])));
        log.info("第三部分：{}", new String(Base64Codec.BASE64.decode(split[2])));
    }

    /**
     * token解析
     */
    @Test
    public void test2(){
        String s = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJWYW5zIiwiaWF0IjoxNjE3MTgzNDYzLCJleHAiOjE2MTcxODM1MjMsIm5hbWUiOiJqaW1teSIsImFzcyI6ImhvbGUifQ.7ngnX8KzDc-tqG5M10J2IdAECBNTiZV0vc3SDZLHgiM";
        Claims claims = (Claims) Jwts.parser().setSigningKey("The deep dark fantasy").parse(s).getBody();
        log.info("id:{}",claims.getId());
        log.info("创建时间:{}",claims.getIssuedAt());
        log.info("用户:{}",claims.getSubject());
        log.info("过期时间:{}",claims.getExpiration());
        log.info("name:{}",claims.get("name"));
        log.info("ass:{}",claims.get("ass"));

    }

    @Test
    public void test3() throws ExecutionException, InterruptedException {
        int[] arr = {1,3,5,7,6,2,12,0};
        for (int i = 0; i < arr.length; i++) {
            int t = arr[i];
            new Thread(()->{
                try {
                    Thread.sleep(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(t);
            }).start();
        }
        Thread.sleep(1000);
    }
}
