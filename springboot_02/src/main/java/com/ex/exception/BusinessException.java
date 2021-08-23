package com.ex.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{

    private Integer code;

    public BusinessException(String message,Integer code) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message,Integer code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
