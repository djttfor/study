package com.ex.server.config;

import com.ex.server.filter.AswaggerFilter;
import com.ex.server.filter.CustomCorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<CustomCorsFilter> setFilter(){
        FilterRegistrationBean<CustomCorsFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new CustomCorsFilter());
        filterBean.setName("CustomCorsFilter");
        filterBean.addUrlPatterns("/*");
        filterBean.setOrder(Integer.MIN_VALUE);
        return filterBean;
    }

//    @Bean
//    public FilterRegistrationBean<AswaggerFilter> setAswaggerFilter(){
//        FilterRegistrationBean<AswaggerFilter> filterBean = new FilterRegistrationBean<>();
//        filterBean.setFilter(new AswaggerFilter());
//        filterBean.setName("AswaggerFilter");
//        filterBean.addUrlPatterns("/*");
//        filterBean.setOrder(Integer.MIN_VALUE+1);
//        return filterBean;
//    }
}
