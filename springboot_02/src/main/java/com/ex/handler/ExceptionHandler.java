package com.ex.handler;

import com.ex.base.ResultAdvice;
import com.ex.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
@Component
public class ExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.error("异常：",ex);
        Integer errorCode ;
        String message;
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            errorCode = businessException.getCode();
            message = businessException.getMessage();
        }else{
            errorCode = 500;
            message = ex.getMessage();
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (PrintWriter writer = response.getWriter()) {
            writer.print(new ObjectMapper().writeValueAsString(ResultAdvice.fail(errorCode,message)));
        }catch (Exception e){
            log.error("响应失败，原因：",e);
        }

        return new ModelAndView();
    }
}
