package com.ex.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //授权
        http.authorizeRequests()
                //设置首页、静态资源、错误页面等过滤
                .antMatchers("/","/css/**","/js/**","/error/**").permitAll()
                .antMatchers("/user/login/**").permitAll()
                //设置拥有角色
                .antMatchers("/user/l1/**").hasRole("v1")
                .antMatchers("/user/l2/**").hasRole("v2")
                .antMatchers("/user/l3/**").hasRole("v3")
                //所有的请求必须认证才能访问，必须登录
                .anyRequest().authenticated();

        //自定义登录页面
        http.formLogin().loginPage("/user/login/page")
        //登录处理页面
        .loginProcessingUrl("/user/login/process")
        //登录成功的页面
        .successForwardUrl("/index")
        //登录成功的页面
        .failureForwardUrl("/user/login/fail");


        //相当于关闭防火墙
        http.csrf().disable();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //应从数据库中读取添加
//        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder()).withUser("jimmy").password(new BCryptPasswordEncoder().encode("123")).roles("v1","v2")
//                .and()
//                .passwordEncoder(new BCryptPasswordEncoder()).withUser("root").password(new BCryptPasswordEncoder().encode("123")).roles("v1","v2","v3")
//                .and()
//                .passwordEncoder(new BCryptPasswordEncoder()).withUser("laowang").password(new BCryptPasswordEncoder().encode("123")).roles("v1");
//
//    }
}
