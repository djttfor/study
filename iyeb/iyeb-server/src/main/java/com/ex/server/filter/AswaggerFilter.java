package com.ex.server.filter;

import com.ex.server.constant.IConstant;
import com.ex.server.util.AuthorizationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Swagger文档debug过滤器
 */
public class AswaggerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String referer = req.getHeader("Referer");
        if(referer==null || "".equals(referer)){
            chain.doFilter(request, response);
            return;
        }

        //swagger2测试专用
        if (referer.endsWith(IConstant.SWAGGER_REFERER)){
            AuthorizationUtil.auth();
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
