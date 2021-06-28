package com.ex.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("/".equals(request.getRequestURI())){
            request.getRequestDispatcher("/index").forward(request,response);
            return false;
        }
        return true;
    }
}
