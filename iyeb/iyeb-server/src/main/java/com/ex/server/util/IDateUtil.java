package com.ex.server.util;

import java.util.Date;

/**
 * 时间类型转换工具
 */
public class IDateUtil {
    private static final int DEFAULT_MUL = 1000;

    /**
     * 生成token的失效时间
     * @param expiration
     * @return
     */
    public static Date generateDefaultTokenExpiration(Long expiration){
        return new Date(System.currentTimeMillis()+expiration*DEFAULT_MUL);
    }

    /**
     * 生成token失效时间，返回毫秒值
     * @param expiration
     * @return
     */
    public static Long generateDefaultTokenExpirationMs(Long expiration){
        return System.currentTimeMillis()+expiration*DEFAULT_MUL;
    }

}
