package com.ex.ssm.config;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 获取异常具体信息
 */
public class ExceptionUtil {

    public static String getStackTrace(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
    public static Throwable getRealException(Throwable t){//获取最里层的异常
        while (null != t){
            Throwable t1 = t.getCause();
            if(null == t1){
                return t;
            }
            t = t1;
        }
        return null;
    }

}
