package com.ex.base;

import com.ex.enums.ErrorEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ResultAdvice<T> {
    private Integer code;
    private String message;
    private T model;
    private Long timestamp;
    private List<T> models;
    

    public ResultAdvice() {
    }

    public ResultAdvice(Integer code, String message, T model) {
        this.code = code;
        this.message = message;
        this.model = model;
        this.timestamp = System.currentTimeMillis();
    }

    public static<T> ResultAdvice<T> create(Integer code,String message,T model){
        return new ResultAdvice<>(code, message,model);
    }

    public static<T> ResultAdvice<T> success(T model){
        return create(ErrorEnum.SUCCESS.getCode(), ErrorEnum.SUCCESS.getMessage(),model);
    }

    public static<T> ResultAdvice<T> fail(){
        return create(ErrorEnum.ERROR.getCode(), ErrorEnum.ERROR.getMessage(),null);
    }
    public static<T> ResultAdvice<T> fail(Integer code,String message){
        return create(code, message,null);
    }
}
