package com.ex.ssm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDate implements Converter<String, Date> {
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
