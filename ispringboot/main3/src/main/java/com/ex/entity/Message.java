package com.ex.entity;

import lombok.Data;

@Data
public class Message {
    Integer code;
    String message;

    public Message() {
        this.code = 0;
        message = "默认信息";
    }
    public Message(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
