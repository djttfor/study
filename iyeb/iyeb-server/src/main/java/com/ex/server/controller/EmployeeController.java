package com.ex.server.controller;


import com.ex.server.dto.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/emp")
public class EmployeeController {
    @GetMapping("/adv")
    public ResponseBean adv(){
        return ResponseBean.success("访问成功");
    }
    @GetMapping("/basic")
    public ResponseBean basic(){
        return ResponseBean.success("访问成功");
    }
}

