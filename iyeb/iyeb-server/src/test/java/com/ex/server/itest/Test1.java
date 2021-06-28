package com.ex.server.itest;

import com.ex.server.constant.IConstant;
import com.ex.server.entity.Admin;
import com.ex.server.entity.SysLog;
import com.ex.server.service.*;
import com.ex.server.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Test1 {
    @Autowired
    AdminService adminService;
    @Autowired
    MenuService menuService;

    @Autowired
    SysLogService sysLogService;

    @Autowired
    AppraiseService appraiseService;

    @Autowired
    MenuRoleService menuRoleService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test1(){
        List<Admin> list = adminService.list();
        for (Admin admin : list) {
            System.out.println(admin);
        }
    }

    @Test
    public void test2(){
        List<SysLog> list = sysLogService.list();
        for (SysLog sysLog : list) {
            System.out.println(sysLog);
        }
    }

    @Test
    public void test3(){
        System.out.println(adminService.selectAdminByName("安娜"));
    }
    @Test
    public void test4(){
        //生成token
        Map<String,Object> claims = new HashMap<>();
        claims.put(IConstant.VERIFY_CODE,"4F5c");
        String token = jwtTokenUtil.generateToken(claims, IConstant.DEFAULT_TIMEOUT);
        redisTemplate.opsForValue().set(token,"4F5c",IConstant.REDIS_DEFAULT_EXPIRED,TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get(token));
    }
    @Test
    public void test5(){
        List<Admin> admins = adminService.queryAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

}

