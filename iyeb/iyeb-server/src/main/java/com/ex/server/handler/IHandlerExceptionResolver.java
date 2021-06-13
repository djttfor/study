package com.ex.server.handler;

import com.ex.server.dto.ResponseBean;
import com.ex.server.enums.ErrorEnum;
import com.ex.server.exception.BusinessException;
import com.ex.server.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
@Slf4j
public class IHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        log.error("全局异常处理===============>");
        // 若响应已响应或已关闭，则不操作
        if (response.isCommitted()) {
            return new ModelAndView();
        }
        Integer errorCode;
        String message;
        if(exception instanceof BusinessException){
            errorCode = ((BusinessException) exception).getCode();
            message = exception.getMessage();
        }else if(exception instanceof NullPointerException){
            errorCode = ErrorEnum.NULL_POINT_EX.getCode();
            message = ErrorEnum.NULL_POINT_EX.getMsg();
        } else{
            errorCode = ErrorEnum.Fail.getCode();
            message = CommonUtil.getRealException(exception);
        }

        log.error("错误码:{},错误信息:{}",errorCode,CommonUtil.getThrowableMessage(exception));
        // 响应类型设置
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        // 响应结果输出
        try (PrintWriter writer = response.getWriter()) {
            writer.write(new ObjectMapper().writeValueAsString(ResponseBean.fail(errorCode,message)));
        } catch (Exception e) {
            log.error("响应输出失败！原因如下：", e);
        }

        return new ModelAndView();
    }
}
