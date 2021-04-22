package com.ex.server.test;

import com.ex.server.entity.*;
import com.ex.server.service.*;

import com.ex.server.util.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

@SpringBootTest
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

    }
    @Test
    public void test4(){
//        String s = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqaW1teSIsImNyZWF0ZWQiOjE2MTkwNTM0OTM2MDEsImV4cCI6MTYxOTY1ODMzM30.5IpwgxrYkmQGMEBnGO2Lk7s7XeYsjcOV-vJfKt3tx98";
//        Claims claims = jwtTokenUtil.parseToken(s);
//        System.out.println(claims.getSubject());
    }

}

class A{
    public static void main(String[] args) throws JsonProcessingException {

    }
}
