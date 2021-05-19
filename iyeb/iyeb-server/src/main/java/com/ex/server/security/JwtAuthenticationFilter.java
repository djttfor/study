package com.ex.server.security;

import com.ex.server.constant.IConstant;
import com.ex.server.util.AuthorizationUtil;
import com.ex.server.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(IConstant.AUTHORIZATION_HEADER);
        //判断是否以Bearer开头
        if (null!=header && header.startsWith(IConstant.AUTH_TOKEN_PREFIX)){
            //获取存储在请求头的token
            String authToken = header.substring(IConstant.AUTH_TOKEN_PREFIX.length());
            //解析token，获取里面的用户名
            String username = jwtTokenUtil.getUsernameByToken(authToken);
            log.info("解析token的用户名为：{}",username);
            //token存在用户名，但是未登录
            if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //看你决定了，可以不选择验证
                //UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //判断token是否还有效
                if (jwtTokenUtil.validateUsernameToken(authToken,username)) {
                    //暂时没设权限
                    AuthorizationUtil.auth(username,null);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
