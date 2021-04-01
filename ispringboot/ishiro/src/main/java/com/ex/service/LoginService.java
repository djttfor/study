package com.ex.service;

import com.ex.entity.User;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public User getUserByName(String name) {
        if("root".equals(name)){
            User user = new User();
            user.setUsername("root");
            user.setPassword("123");
            return user;
        }
        return null;
    }
}
