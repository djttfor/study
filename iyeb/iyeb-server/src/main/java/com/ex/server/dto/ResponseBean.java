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
    private Object object;

    public static ResponseBean response(Integer code,String message,Object object){
        return new ResponseBean(code,message,object);
    }

    public static ResponseBean success(String message){
        return response(200,message,null);
    }

    public static ResponseBean fail(String message){
        return new ResponseBean(500,message,null);
    }

}
