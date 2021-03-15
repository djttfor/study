package com.ex.ssm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ex.ssm.entity.Demo;
import com.ex.ssm.entity.Message;
import com.ex.ssm.entity.User;
import com.ex.ssm.entity.Vo;
import com.ex.ssm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-03-11
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/home")
    public String goHome(Demo demo, HttpServletRequest request, HttpServletResponse response){
        System.out.println("传递的参数："+demo.getName()+","+demo.getPass());
        System.out.println(request.getRemoteHost());
        // request.setAttribute("message","传递的参数："+name+","+b);
        HttpSession session = request.getSession();
        session.setAttribute("message","传递的参数："+demo.getName()+","+demo.getPass());
        return "home";
    }
    @PostMapping("/home2")
    public void goHome2( Vo vo){
        System.out.println("传递的参数："+vo);
    }

    @PostMapping("/home3")
    @ResponseBody
    public Message goHome3(@RequestBody Demo demo){
        log.info("收到的参数：{}",demo);
        // request.setAttribute("message","传递的参数："+name+","+b);
        return new Message();
    }
    @GetMapping("/home4/{a}/{b}")
    @ResponseBody
    public Message goHome4(@PathVariable("a") String a,
                           @PathVariable("b") String b){
        log.info("从前台收到的信息{},{}",a,b);
        return new Message();
    }
    @PostMapping("/home5")
    @ResponseBody
    public Message goHome5(){
        return new Message();
    }
    @PostMapping("/home6")
    @ResponseBody
    public Message goHome6(@RequestBody Message message){
        log.info("从前台拿到的数据：{}",message);
        return new Message();
    }
    @PostMapping("/home7")
    @ResponseBody
    public Message goHome7(@RequestBody String name){
        log.info("从前台拿到的数据：{}",name);
        return new Message();
    }
    @GetMapping("/home8/{current}/{pageSize}")
    @ResponseBody
    public Page<User> goHome8(@PathVariable("current") int current,
                              @PathVariable("pageSize") int pageSize){
        return userService.pageQuery(current,pageSize);
    }
    public void goHome9(){
        System.out.println("所有兄弟都会来打你的，dev");
    }
}
