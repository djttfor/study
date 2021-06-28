package com.ex.server.constant;

public interface IConstant {
    //验证码Cookie名称
    String VALIDATE_CODE_COOKIE_NAME = "VCodeCookie";
    String VERIFY_CODE = "code";
    Long DEFAULT_TIMEOUT = 60*60*24L;
    Long REDIS_DEFAULT_EXPIRED = 60L;
    //认证请求头
    String AUTHORIZATION_HEADER = "Authorization";
    //token字符串的前缀
    String AUTH_TOKEN_PREFIX = "Bearer";
    //角色前缀
    String ROLE_PREFIX = "ROLE_";
    //swagger2的referer路径
    String SWAGGER_REFERER = "localhost:81/doc.html";
    //用户权限与角色信息过期时间expiration
    Long AUTH_EXPIRATION_TIME = 60*60*24*7L;
}
