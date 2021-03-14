package com.ex.ssm.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.ObjectUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToString implements Converter<Date,String > {
    @Override
    public String convert(Date source) {
        DateFormat dateFormat;
        if(!ObjectUtils.isEmpty(source)){
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(source);
        }
        return null;
    }
}
