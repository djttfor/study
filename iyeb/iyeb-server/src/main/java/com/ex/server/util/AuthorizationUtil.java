package com.ex.server.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Collection;

/**
 * 一些骚东西
 */
public class AuthorizationUtil {
    /**
     * 设置验证与授权
     * @param username 用户名
     * @param authorities 权限
     */
    public static void auth(String username, Collection<? extends GrantedAuthority> authorities){
        Principal principal = () -> username;
        //更新springSecurity对象，将userDetails，暂时没有权限验证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(principal,null,authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    /**
     * 给JwtAuthenticationFilter使用
     * @param username
     */
    public static void auth(String username){
        auth(username,null);
    }

    /**
     * swagger2测试用
     */
    public static void auth(){
        auth("test",null);
    }
}
