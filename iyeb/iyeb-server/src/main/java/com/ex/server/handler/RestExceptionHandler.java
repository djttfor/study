package com.ex.server.handler;

import com.ex.server.dto.ResponseBean;
import com.ex.server.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {
    /**
     * 400错误
     */
    @ExceptionHandler({HttpMessageConversionException.class})
    @ResponseBody
    public ResponseBean request400(HttpMessageConversionException ex) {
        log.error("400异常 {}", ex.getMessage());
        return ResponseBean.fail(ErrorEnum.PARAM_ERROR);
    }

    /**
     * 415错误
     */
    @ExceptionHandler({HttpMediaTypeException.class})
    @ResponseBody
    public ResponseBean request415(HttpMediaTypeException ex) {
        log.error("415异常 {}", ex.getMessage());
        return ResponseBean.fail(ErrorEnum.PARAM_TYPE_ERROR);
    }
}
