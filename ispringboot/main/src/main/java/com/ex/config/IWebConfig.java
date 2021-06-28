package com.ex.config;

import com.ex.interceptor.IndexInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class IWebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDate());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IndexInterceptor())
                .addPathPatterns("/**");
    }

    static class StringToDate implements Converter<String, Date> {
        private static final Logger logger = LoggerFactory.getLogger(StringToDate.class);
        @Override
        public Date convert(String source) {
            SimpleDateFormat simpleDateFormat ;
            if(!StringUtils.isEmpty(source)){
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return simpleDateFormat.parse(source);
                } catch (ParseException e) {
                    logger.info("这个值不能转为日期,Value:"+source,e);
                }
            }
            return null;
        }
    }
}
