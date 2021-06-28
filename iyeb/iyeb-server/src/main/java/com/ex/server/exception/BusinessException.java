package com.ex.server.exception;

import com.ex.server.enums.ErrorEnum;

public class BusinessException extends RuntimeException  {

    private static final long serialVersionUID = -8030481791244849827L;

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ErrorEnum errorEnum){
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
    }

    public BusinessException(Throwable t){super(t);}

    public BusinessException(String message, Throwable t){
        super(message,t);
    }

    public BusinessException(String message, boolean enableStackTrace) {
        super(message, null,false, enableStackTrace);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
