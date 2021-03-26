package com.ex.config;

import com.ex.service.AccountService;
import com.ex.service.UserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IConfig {


//    @Bean
//    public UserService buildUserService(){
//        return new UserService("默认创建");
//    }

    @Bean
    @ConditionalOnBean(UserService.class)
    @ConditionalOnMissingBean(name = "buildUserService2",value = AccountService.class)
    public UserService buildUserService2(){
        return new UserService("第二次创建");
    }

    @Bean
    public AccountService buildAccountService(){
        return new AccountService("默认创建");
    }

}
