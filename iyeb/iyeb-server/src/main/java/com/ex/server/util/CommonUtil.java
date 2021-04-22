package com.ex.server.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CommonUtil {
    /**
     * 获取异常堆栈信息
     * @param t
     * @return
     */
    public static String getThrowableMessage(Throwable t){
        String message = null;
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            message = sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (pw !=null) {
                pw.close();
            }
        }
        return message;
    }
}
