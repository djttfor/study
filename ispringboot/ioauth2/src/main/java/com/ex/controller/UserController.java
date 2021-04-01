package com.ex.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getCurrent")
    public Object getCurrent(Authentication authentication){
        return authentication.getPrincipal();
    }
}
