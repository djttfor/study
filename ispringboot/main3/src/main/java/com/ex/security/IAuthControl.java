package com.ex.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Slf4j
@Component
public class IAuthControl{
    public Boolean hasPermission(HttpServletRequest request,Authentication authentication) {
        String uri = request.getRequestURI();
        log.info("当前uri：{}",uri);
        //获取userDetail
        Object principal = authentication.getPrincipal();
        //判断用户是否已经登录，不是匿名用户
        if(principal instanceof UserDetails){
            //获取该用户拥有的权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            //判断该用户是否拥有此路径权限，这里仅仅模拟
            //真正的场景是要到数据库查询权限进行验证
            boolean v1 = authorities.contains(new SimpleGrantedAuthority("v1"));
            if(v1) {
                log.info("可以访问");
            }
            return v1;
        }
        log.info("拒绝访问");
        //拒绝访问
        return false;
    }
}
