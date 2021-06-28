package com.mvc.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("<<<<<<<<<<拦截器前置处理:"+handler.getClass().getName());
        String uri = request.getRequestURI();
        int length = request.getContextPath().length();
        String simplifiedUrl = uri.substring(length);
        System.out.println("simplifiedUrl:"+simplifiedUrl);
        if ("/haha/toHello3".equals(simplifiedUrl)){
            System.out.println("可以通行");
            return true;
        }else{
            System.out.println("禁止通行");
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("<<<<<<<<<<拦截器后置处理执行了");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("<<<<<<<<<<<拦截器最终处理执行了");
    }
}
