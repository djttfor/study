package com.ex.controller;

import com.ex.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/all")
    public String queryAll(Map<String,Object> map){
        List<User> query = jdbcTemplate.query("select * from user", (resultSet, i) -> {
            User user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setPhone(resultSet.getString("phone"));
            user.setAddress(resultSet.getString("address"));
            return user;
        });
        map.put("users",query);
        return "user";
    }
    @GetMapping("/one")
    public String queryOne(Map<String,Object> map){
        List<User> one = jdbcTemplate.query("select * from user where id = ?", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                return user;
            }
        }, 100);
        map.put("user",one.get(0));
        return "user";
    }
    @PostMapping("/u")
    public String lu(String name, String date){
        log.info("name:{},时间:{}",name,date);
        return "index";
    }
}
