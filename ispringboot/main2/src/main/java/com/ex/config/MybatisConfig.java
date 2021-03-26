package com.ex.config;

import com.ex.plugin.SQLExtractLog;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@MapperScan("com.ex.mapper")
public class MybatisConfig {

    @Bean
    public Interceptor buildMyInterceptor(){
        return new SQLExtractLog();
    }


    @Bean
    public Interceptor buildPageHelper(){
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect","mysql");
        properties.setProperty("reasonable","true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
}
