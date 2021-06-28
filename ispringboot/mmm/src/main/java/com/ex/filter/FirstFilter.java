package com.ex.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Slf4j
@Order(3000)
@WebFilter(urlPatterns = "/*",filterName = "iFirstFilter")
public class FirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("iFirstFilter被初始化了");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("iFirstFilter执行了");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        log.info("iFirstFilter被销毁了");
    }
}
