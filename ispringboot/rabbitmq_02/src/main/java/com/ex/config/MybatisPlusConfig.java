package com.ex.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ex.mapper")
public class MybatisPlusConfig {
}
