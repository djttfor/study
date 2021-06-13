package com.ex.server.exception;

import com.ex.server.enums.ErrorEnum;
import org.springframework.security.access.AccessDeniedException;

public class IAccessDeniedException extends AccessDeniedException {

    private Integer code;

    public IAccessDeniedException(ErrorEnum errorEnum){
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
    }

    public IAccessDeniedException(String msg) {
        super(msg);
    }

    public IAccessDeniedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
