package com.ex.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Bird {
    @Value("gugu")
    private String name;
    private Integer age;
    @Value("广州")
    private String address;
    private Date birthday;
    private User user;
    private Account account;

    @Override
    public String toString() {
        return "Bird{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", user=" + user +
                ", account=" + account +
                '}';
    }

}
