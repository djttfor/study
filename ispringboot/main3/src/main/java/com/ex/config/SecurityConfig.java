package com.ex.config;

import com.ex.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.io.PrintWriter;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource(name = "iUserDetailServiceImpl")
    UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/user/all");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //授权
        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .authorizeRequests().antMatchers("/","/css/**","/js/**","/error/**").permitAll()
                .antMatchers("/user/login/**").permitAll()
                .antMatchers("/user/getVisitCount").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login/page")


                //.loginProcessingUrl()
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("{\"status\":\"ok\",\"msg\":\"登录成功\"}");
                    out.flush();
                    out.close();
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("{\"status\":\"error\",\"msg\":\"登录失败\"}");
                    out.flush();
                    out.close();
                })
                .loginProcessingUrl("/user/login/ass")
                .usernameParameter("username")
                .passwordParameter("password").permitAll()
                .and().csrf().disable()
                .logout().logoutUrl("/exit").logoutSuccessHandler(
                        (request, response, authentication) -> {
                            response.setContentType("application/json;charset=utf-8");
                            PrintWriter out = response.getWriter();
                            String s = new ObjectMapper().writeValueAsString(new Message(1,"登出成功"));
                            out.write(s);
                            out.flush();
                            out.close();
        }).permitAll();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //在数据库中存取密码的加密方式
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

//    @Bean
//    public PersistentTokenRepository buildPersistentTokenRepository(DataSource dataSource){
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setDataSource(dataSource);
//
//        //自动建表,每次启动都回去建表，所以第二次要注释掉
//       // jdbcTokenRepository.setCreateTableOnStartup(true);
//
//        return jdbcTokenRepository;
//    }
//    @Bean
//    public FilterRegistrationBean<IFilter> buildFilter(){
//        FilterRegistrationBean<IFilter> filter = new FilterRegistrationBean<>();
//        filter.setFilter(new IFilter());
//        filter.setOrder(Integer.MIN_VALUE);
//        filter.setName("IFilter");
//        filter.addUrlPatterns("/*");
//        return filter;
//    }
}
