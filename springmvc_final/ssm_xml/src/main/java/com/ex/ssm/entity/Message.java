package com.ex.ssm.entity;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 2485368784558712168L;
    private Integer code;
    private String message;

    public Message() {
        this.code = 0;
        this.message = "用户名或密码错误";
    }

    @Override
    public String toString() {
        return "Message{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
