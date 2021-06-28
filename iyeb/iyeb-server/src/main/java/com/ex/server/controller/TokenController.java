package com.ex.server.controller;

import com.ex.server.constant.IConstant;
import com.ex.server.util.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @ApiOperation(value = "验证码令牌")
    @GetMapping(value = "/codeToken")
    public String generateToken(){
        //生成token
        Map<String,Object> claims = new HashMap<>();
        claims.put(IConstant.VERIFY_CODE,"King");
        return jwtTokenUtil.generateToken(claims, IConstant.DEFAULT_TIMEOUT);
    }
}
