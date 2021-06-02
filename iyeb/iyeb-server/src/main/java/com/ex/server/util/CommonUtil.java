package com.ex.server.util;

import com.ex.server.constant.IConstant;
import com.ex.server.entity.Admin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

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

    public static void saveSession(HttpServletRequest request, HttpServletResponse response){
        String id = request.getSession().getId();
        Cookie sessionCookie = findCookieByName("JSESSIONID", request.getCookies());
        sessionCookie.setMaxAge(60*60);
        sessionCookie.setPath("/");
        sessionCookie.setDomain("test.demo");
        response.addCookie(sessionCookie);
    }

    public static Cookie findCookieByName(String name,Cookie[] cookies){
        if (cookies==null||cookies.length==0|| !StringUtils.hasLength(name)){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    public static void setCookie(String cookieName,String value,HttpServletResponse response){
        Cookie cookie = new Cookie(cookieName,value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getCookieValue(String cookieName,HttpServletRequest request){
        Cookie cookie = findCookieByName(cookieName, request.getCookies());
        if(cookie !=null){
            return cookie.getValue();
        }
        return null;
    }

    public static void destroyCookie(String cookieName,HttpServletRequest request){
        Cookie cookie = findCookieByName(cookieName, request.getCookies());
        if(cookie !=null){
            cookie.setMaxAge(-1);
        }
    }

    public static Admin getPrincipal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication!=null){
            return (Admin) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 从请求中获取已认证的令牌
     * @param request
     * @return
     */
    public static String getUsernameTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader(IConstant.AUTHORIZATION_HEADER);
        if (header!=null){
            if (header.startsWith(IConstant.AUTH_TOKEN_PREFIX)) {
                return header.substring(IConstant.AUTH_TOKEN_PREFIX.length());
            }
        }
        return null;
    }

    /**
     * 获取没有前缀的角色名称
     * @param roleName
     * @return
     */
    public static String getUnprefixedRoleName(String roleName){
        return roleName.substring(IConstant.ROLE_PREFIX.length());
    }

    /**
     * 获取UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }


}
