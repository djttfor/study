package com.ex.controller;

import com.ex.entity.Message;
import com.ex.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/l1/{l}")
    public String l1(@PathVariable("l")String l){
        log.info("当前访问的是：{}","L1/"+l);
        return "L1/"+l;
    }
    @RequestMapping("/l2/{l}")
    public String l2(@PathVariable("l")String l){
        log.info("当前访问的是：{}","L1/"+l);
        return "L1/"+l;
    }
    @RequestMapping("/l3/{l}")
    public String l3(@PathVariable("l")String l){
        log.info("当前访问的是：{}","L1/"+l);
        return "L1/"+l;
    }
    @RequestMapping("/test")
    public String toTest(){
        return "login";
    }


    @RequestMapping("/login/page")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login/fail")
    public String toLoginFail(){
        return "loginFail";
    }

    @RequestMapping("/add")
    public String toAdd(){
        return "user/add";
    }

    @PostMapping("/login/process")
    @ResponseBody
    public Message loginProcess(User user,boolean rememberMe){
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return new Message(1,"请输入用户名和密码！");
        }

        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        log.info("principal:{}",principal);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUsername(),
                user.getPassword()
        );
        //记住我
        if(rememberMe){
            usernamePasswordToken.setRememberMe(true);
        }
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
            return new Message(1,"用户名不存在！");
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            return new Message(1,"账号或密码错误！");
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            return new Message(1,"没有权限");
        }
        return new Message(2,"login success");
    }
    @RequestMapping("/logout")
    @ResponseBody
    public Message logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipal()!=null){
            subject.logout();
            return new Message(3,"登出成功");
        }
        return new Message(3,"请先登录");
    }

    @RequestMapping("/unauth")
    public String unauth(){
        return "unauth";
    }

}
