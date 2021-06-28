package com.ex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    Integer code;
    String message;

    public Message() {
        this.code = 0;
        message = "默认信息";
    }
}
