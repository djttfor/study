package com.ex.ssm.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Birthday {
    private String name;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date date ;

    @Override
    public String toString() {
        return "Birthday{" +
                "name='" + name + '\'' +
                ", date=" + date +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
