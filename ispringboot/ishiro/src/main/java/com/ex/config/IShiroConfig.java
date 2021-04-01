package com.ex.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class IShiroConfig {

    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean buildShiroFilterFactory(WebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        /*
        shiro的内置过滤器
        anon：无需认证可以访问
        authc：必须认证了才能访问
        user：必须拥有记住我功能才能使用
        perms：拥有对某个资源的权限才能访问
        role：拥有某个角色才能访问
         */
        Map<String,String> filterMap = new LinkedHashMap<>();

        //设置主页不需要认证，需设在前面
        filterMap.put("/","anon");
        //设置所有路径都必须要经过认证
        filterMap.put("/*","authc");
        filterMap.put("/user/login/**","anon");

        filterMap.put("/user/l1/**","perms[l1]");
        filterMap.put("/user/l2/**","perms[l2]");
        filterMap.put("/user/l3/**","perms[l3]");

        //登出
       // filterMap.put("/user/logout","logout");

        //添加验证逻辑
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        //设置登录界面
        shiroFilterFactoryBean.setLoginUrl("/user/login/page");

        //设没有权限时访问的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/unauth");

        return shiroFilterFactoryBean;
    }

    //defaultWebSecurityManager
    @Bean
    public WebSecurityManager buildDWSM(UserRealm userRealm,CookieRememberMeManager cookieRememberMeManager){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);
        return defaultWebSecurityManager;
    }

    @Bean
    public CookieRememberMeManager buildCookieRememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie cookie = new SimpleCookie("iRememberMe");
        cookie.setMaxAge(10*60);
        cookieRememberMeManager.setCookie(cookie);
        return cookieRememberMeManager;
    }

    //自定义Realm
    @Bean
    public UserRealm buildUserRealm(){
        UserRealm userRealm = new UserRealm();
        userRealm.setCachingEnabled(true);
        return userRealm;
    }
}
