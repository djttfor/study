package com.ss.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ComponentScan(value = "com.ss",
        excludeFilters={@ComponentScan.Filter(type = FilterType.ANNOTATION,value = {Controller.class, RestController.class})})
public class RootConfig {
}
