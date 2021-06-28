package com.ex.server.controller;


import com.ex.server.dto.LoginParam;
import com.ex.server.dto.ResponseBean;
import com.ex.server.entity.Admin;
import com.ex.server.service.AdminService;
import com.ex.server.util.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
public class LoginController {

    @Autowired
    AdminService adminService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @ApiOperation(value = "登录验证,验证成功返回token")
    @PostMapping("/login")
    public ResponseBean login(@RequestBody LoginParam loginParam , HttpServletRequest request, HttpServletResponse response){
        return adminService.login(loginParam,request,response);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public ResponseBean logout(HttpServletRequest request){
        //获取令牌
        String token = CommonUtil.getUsernameTokenFromRequest(request);
        if (token != null){
            redisTemplate.delete(token);
        }
        return ResponseBean.success("登出成功");
    }


}
