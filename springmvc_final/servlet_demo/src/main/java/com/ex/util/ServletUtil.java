package com.ex.util;

import com.ex.custom.MyException;
import com.ex.custom.User;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class ServletUtil {
    public static Cookie findCookieByName(String cookieName, Cookie[] cookies) {
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    public static String getExceptionMessage(Throwable t) {
        if (null == t) {
            return null;
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);

        return sw.toString();
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("AaBBBBBB",1);
        map.put("AaAaBBBB",2);
        map.put("AaAaAaBB",3);
        map.put("AaAaAaAa",4);
        map.put("BBAaAaAa",5);
        map.put("BBBBAaAa",6);
        map.put("BBBBBBAa",7);
        map.put("BBBBBBBB",8);
        map.put("AaBBAaBB",9);
        map.put("BBAaAaBB",10);
        map.put("BBAaBBAa",11);
        map.put("AaBBBBAa",12);
        map.put("AaBBBBAaa",12);
        map.put("BBAaBBAaab",12);
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            System.out.println(stringObjectEntry.getKey()+","+stringObjectEntry.getValue());
        }
        System.out.println(map.size());
    }

}
