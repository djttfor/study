package com.ex.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "hehe")
public class Hehe {
    String name;
    String sex;
    Integer age;
    List<Map<String,String>> hehe1;

    public List<Map<String, String>> getHehe1() {
        return hehe1;
    }

    public void setHehe1(List<Map<String, String>> hehe1) {
        this.hehe1 = hehe1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Hehe{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", hehe1=" + hehe1 +
                '}';
    }
}
