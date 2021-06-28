//package com.ex.config;
//
//import com.ex.filter.FirstFilter;
//import com.ex.filter.SecondFilter;
//import com.ex.filter.ThirdFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//    @Bean
//    public FilterRegistrationBean<ThirdFilter> setFilter(){
//        FilterRegistrationBean<ThirdFilter> filterBean = new FilterRegistrationBean<>();
//        filterBean.setFilter(new ThirdFilter());
//        filterBean.setName("iThirdFilter");
//        filterBean.addUrlPatterns("/*");
//        filterBean.setOrder(500);
//        return filterBean;
//    }
//    @Bean
//    public FilterRegistrationBean<SecondFilter> setFilter2(){
//        FilterRegistrationBean<SecondFilter> filterBean = new FilterRegistrationBean<>();
//        filterBean.setFilter(new SecondFilter());
//        filterBean.setName("iSecondFilter");
//        filterBean.addUrlPatterns("/*");
//        filterBean.setOrder(5000);
//        return filterBean;
//    }
//    @Bean
//    public FilterRegistrationBean<FirstFilter> setFilter3(){
//        FilterRegistrationBean<FirstFilter> filterBean = new FilterRegistrationBean<>();
//        filterBean.setFilter(new FirstFilter());
//        filterBean.setName("iFirstFilter");
//        filterBean.addUrlPatterns("/*");
//        filterBean.setOrder(50000);
//        return filterBean;
//    }
//}
//
