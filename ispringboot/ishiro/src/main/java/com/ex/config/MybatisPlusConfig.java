package com.ex.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.ex.plugin.SQLExtractLog;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.LocalCacheScope;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ex.mapper")
public class MybatisPlusConfig {
    @Bean
    public Interceptor buildMyInterceptor(){
        return new SQLExtractLog();
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.setCacheEnabled(false);
            configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
        };
    }
}
