package com.ex.ssm.interceptor;


import com.ex.ssm.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    public final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if("on".equals(request.getParameter("remember"))){
            if(session.getAttribute("user")==null){
                User user = new User();
//                user.setUsername(request.getParameter("username"));
//                user.setPassword(request.getParameter("password"));
//                user.setRemember(true);
                session.setAttribute("user",user);
                logger.info("登录拦截器启用记录密码");
            }
        }else{
            session.removeAttribute("user");
            logger.info("拦截器停用了记录密码");
        }
        return true;
    }
}
