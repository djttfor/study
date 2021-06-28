package com.ex.server.security;

import com.ex.server.filter.AswaggerFilter;
import com.ex.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AdminService adminService;

    /**
     * springsecurity的主要配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //使用jwt不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //剩下的请求都拦截
                .anyRequest()
                .authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(buildAccessDecisionManager());
                        return object;
                    }
                });
        //添加jwt的拦截器，登录授权过滤器
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //用于swagger2测试请求
        http.addFilterAfter(new AswaggerFilter(),SecurityContextPersistenceFilter.class);

        //当未登录或token失效时的返回
        http.exceptionHandling()
                //未登录时，返回提示
                .accessDeniedHandler(accessDeniedHandler())
                //权限不足时返回提示
                .authenticationEntryPoint(authenticationEntryPoint());
    }

    /**
     * 用于用户登录验证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //指定用户登录的验证逻辑和密码编码器
        auth.userDetailsService(buildUserDetailsService()).passwordEncoder(buildPasswordEncoder());
    }

    /**
     * 主要用于放行路径，不走拦截链路
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行Swagger路径
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/css/**",
                "/js/**",
                "/index.html",
                "/favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha",
                "/export",
                "/ws/**",
                "/email/**",
                "/topic/**",
                "/codeToken",
                "/ws/**",
                "/rawChat/**"
        );
    }

    /**
     * 重写springSecurity的登录验证逻辑
     * @return 自定义的UserDetails
     */
    @Bean
    public UserDetailsService buildUserDetailsService(){
        return username ->  adminService.selectAdminByName(username);
    }

    /**
     * 密码加密与解密
     * @return
     */
    @Bean
    public PasswordEncoder buildPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * jwt登录授权过滤器
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    /**
     * 自定义权限验证
     * @return
     */
    @Bean
    public AccessDecisionManager buildAccessDecisionManager(){
        return new AccessDecisionManagerImpl();
    }

    /**
     * 没有权限时的自定义提示
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new RestAccessDeniedHandler();
    }
    /**
     * 没有登录时的自定义提示
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new RestAuthenticationEntryPoint();
    }
}
