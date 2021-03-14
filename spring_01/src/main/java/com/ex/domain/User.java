package com.ex.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class User {
    public User() {
        System.out.println("User被创建了");
    }

    //@Autowired
    @Resource(name = "account")
    private Account account;

    @Value("123")
    String id;
    @Value("jimmy")
    String name;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "account=" + account +
//                ", id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                '}';
//    }
}
