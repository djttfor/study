package com.ex.ssm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ex.ssm.entity.Birthday;
import com.ex.ssm.entity.Message;
import com.ex.ssm.entity.User;
import com.ex.ssm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Message loginCheck(@RequestBody User user){
        Message message = new Message();
        if (null==user.getUsername()||null==user.getPassword()){
            return message;
        }

        if(null!=userService.loginCheck(user)){
            message.setCode(1);
            message.setMessage("登录成功");
        }else{
            message.setMessage("用户名或密码错误");
        }
        return message;
    }
    @PostMapping("/login2")
    @ResponseBody
    public Message loginCheck(){
        return new Message();
    }

    @GetMapping("/all")
    @ResponseBody
    public List<User> all(){
        return userService.list();
    }

    @PostMapping("/all/{current}/{pageSize}")
    public Page<User> all(@PathVariable("current") int current,@PathVariable("pageSize") int pageSize){
        return userService.selectPage(current,pageSize);
    }
    @PostMapping("/like/{current}/{pageSize}")
    public Page<User> selectLikeName(@RequestBody User user,
                                     @PathVariable("current") int current,@PathVariable("pageSize") int pageSize){
        return userService.selectLikePage(user,current,pageSize);
    }
    @PostMapping("/br")
    public Birthday dateTest(@RequestBody Birthday birthday) throws ParseException {
        log.info("前台发来的生日对象收到了：{}",birthday);
        return birthday;
    }
    @GetMapping("/gall")
    public Page<User> gAll(int c,int p){
        return userService.selectPage(c,p);
    }


}
