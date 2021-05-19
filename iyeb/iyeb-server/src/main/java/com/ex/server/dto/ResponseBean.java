package com.ex.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean {
    private Integer code;
    private String message;
    private Object result;

    public static ResponseBean response(Integer code,String message,Object object){
        return new ResponseBean(code,message,object);
    }
    public static ResponseBean success(String message){
        return response(20000,message,null);
    }

    public static ResponseBean success(Object o){
        return response(20000,"",o);
    }

    public static ResponseBean success(String message,Object o){
        return response(20000,message,o);
    }

    public static ResponseBean fail(String message){
        return new ResponseBean(50000,message,null);
    }

}
