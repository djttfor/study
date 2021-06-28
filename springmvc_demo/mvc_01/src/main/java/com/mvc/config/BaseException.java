package com.mvc.config;

public class BaseException extends Exception {
    Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BaseException() {
    }

    public BaseException(String message){
       super(message,null,false,false);
    }

    /**
     * 自定义错误码,及自定义错误信息
     * @param code 错误码
     * @param message 错误信息
     */
    public BaseException(Integer code, String message) {
        super(message,null,false,false);
        this.code = code;
    }

    /**
     * 自定义错误信息,是否追踪堆栈异常
     * @param message 错误信息
     * @param recordStackTrace 是否追踪堆栈异常
     */
    public BaseException(String message ,boolean recordStackTrace){
        super(message,null,false,recordStackTrace);
    }

}
