package com.ex.server.security;

import com.ex.server.dto.ResponseBean;
import com.ex.server.exception.IAccessDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        String message;
        Integer code;
        if(accessDeniedException instanceof IAccessDeniedException){
            IAccessDeniedException ide = (IAccessDeniedException) accessDeniedException;
            message = ide.getMessage();
            code = ide.getCode();
        } else if(accessDeniedException.getMessage()!=null){
            message = accessDeniedException.getMessage();
            code = 50000;
        }else{
            code = 50000;
            message = "权限不足，请联系管理员";
        }
        ResponseBean responseBean = ResponseBean.fail(code,message);
        writer.write( new ObjectMapper().writeValueAsString(responseBean));
        writer.flush();
        writer.close();
    }
}
