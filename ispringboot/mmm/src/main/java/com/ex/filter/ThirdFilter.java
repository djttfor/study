package com.ex.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Slf4j
@Order(500)
@WebFilter(urlPatterns = "/user",filterName = "iThirdFilter")
public class ThirdFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("iThirdFilter被初始化了");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("iThirdFilter执行了");
        //chain.doFilter(request,response);
        request.getRequestDispatcher("404.html").forward(request,response);
        return;
    }

    @Override
    public void destroy() {
        log.info("iThirdFilter被销毁了");
    }
}
