package com.ex.filter;

import com.ex.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter("/*")
public class LoginFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String accessUri = req.getRequestURI();
        log.info("拦截的路径:{}",accessUri);
        //静态资源或者首页都可以访问
        if(accessUri.startsWith(req.getContextPath()+"/js")
        ||accessUri.startsWith(req.getContextPath()+"/image")
        ||accessUri.endsWith("index.html")
        ||accessUri.equals(req.getContextPath())
        ||accessUri.equals(req.getContextPath()+"/")
        ||accessUri.endsWith("login.html")
        ||accessUri.endsWith("hello/login")
        ||accessUri.endsWith("fail.html"))
        {
            chain.doFilter(request,response);
            return;
        }
        Cookie remember = ServletUtil.findCookieByName("remember", req.getCookies());
        //检查Cookie有没有签发的登录token
        if(remember==null){
            //没有cookie,跳转到登录界面
            log.info("您尚未登录,请您先登录");
            resp.sendRedirect(req.getContextPath()+"/page/login.html");
        }else{
            if (!"approve".equals(remember.getValue())) {
                log.info("您需要重新登录");
                resp.sendRedirect(req.getContextPath()+"/page/login.html");
            }else{
                log.info("用户已登录");
            }

            //resp.sendRedirect(req.getContextPath()+"/page/success.html");
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
