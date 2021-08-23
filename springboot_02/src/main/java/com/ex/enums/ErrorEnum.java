package com.ex.enums;

public enum ErrorEnum {
    SUCCESS("调用成功",200),
    ERROR("调用异常",500);
    private String message;
    private Integer code;

    ErrorEnum(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
