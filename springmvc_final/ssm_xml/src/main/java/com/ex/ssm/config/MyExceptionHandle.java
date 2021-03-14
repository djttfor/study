package com.ex.ssm.config;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyExceptionHandle implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();

        if (ex instanceof BaseException){
            BaseException baseException = (BaseException) ex;
            mv.addObject("errMessage",baseException.getMessage());
            mv.addObject("stackInfo",ExceptionUtil.getStackTrace(baseException));
        }else{
            mv.addObject("errMessage", ex.getMessage());
            mv.addObject("stackInfo",ExceptionUtil.getStackTrace(ex));
        }

        mv.setViewName("404");
        return mv;
    }
}
