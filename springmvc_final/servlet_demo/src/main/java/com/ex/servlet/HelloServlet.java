package com.ex.servlet;

import com.ex.custom.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ex.util.ServletUtil;

@WebServlet("/hello/*")
public class HelloServlet extends BaseServlet {
    private static final Logger log = LoggerFactory.getLogger(HelloServlet.class);

    public void sayHello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("www.baidu.com");
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        if("jimmy".equals(username)&&"123".equals(password)){
            if("on".equals(remember)){
                Cookie rememberCookie = ServletUtil.findCookieByName("remember", request.getCookies());
                if (rememberCookie == null) {
                    try {
                        rememberCookie = new Cookie("remember","approved");
                        rememberCookie.setMaxAge(60*60);
                        rememberCookie.setPath("/");
                        response.addCookie(rememberCookie);
                    } catch (Exception e) {
                        log.error("添加cookie出错了当前cookie:{},异常:{}",rememberCookie,e);
                        throw new MyException("添加cookie出错了当前cookie:,异常:"+rememberCookie.getName()
                                +","+rememberCookie.getValue(),e);
                    }
                }
            }
            response.sendRedirect(request.getContextPath()+"/page/success.html");
        }else{
            response.sendRedirect(request.getContextPath()+"/page/fail.html");
        }

    }

}
