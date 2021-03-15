package com.ex.ssm.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Vo {
    private String name;
    private Integer age;
    private Date birthday;
    private Demo demo;
    private List<User> userList;
    private Map<String,String> map;
    private Map<String,User> userMap;

    @Override
    public String toString() {
        return "Vo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", demo=" + demo +
                ", userList=" + userList +
                ", map=" + map +
                ", userMap=" + userMap +
                '}';
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Demo getDemo() {
        return demo;
    }

    public void setDemo(Demo demo) {
        this.demo = demo;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
