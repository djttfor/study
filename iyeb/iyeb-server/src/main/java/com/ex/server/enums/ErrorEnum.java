package com.ex.server.enums;

import java.util.HashMap;
import java.util.Map;

public enum ErrorEnum {

    SUCCESS(20000,"操作成功"),
    Fail(50000,"服务器出错了，请联系管理员"),
    NULL_POINT_EX(40000,"空指针异常"),
    PARAM_ERROR(50001,"参数错误,传递的类型无法解析"),
    PARAM_TYPE_ERROR(50004,"参数类型无法解析"),
    PERMISSION_DENIED(50002,"权限不足,请联系管理员"),
    LOGIN_EXPIRED(50003,"登录过期,请重新登录"),
    WEB_SOCKET_AUTH_ERROR(50004,"websocket连接验证失败"),
    USERNAME_PASSWORD_ERROR(50005,"用户名或密码不正确"),
    VERIFICATION_CODE_EXPIRED(50006,"验证码过期"),
    VERIFICATION_CODE_ERROR(50007,"验证码错误，请重新输入"),
    LOGIN_PARAM_NULL(50008,"用户名、密码、验证码不能为空"),
    ACCOUNT_DISABLE(50009,"账号未启用"),
    ACCOUNT_LOCKED(50010,"账号已被锁定，请联系管理员"),
    ACCOUNT_EXPIRED(50011,"账号已过期"),
    WEB_SOCKET_CONNECT_ERROR(50012,"websocket连接已关闭")



;
    private final Integer code;
    private final String msg;

    private static final Map<Integer,ErrorEnum> errorEnumMap = new HashMap<>();

    static {
        for (ErrorEnum value : ErrorEnum.values()) {
            errorEnumMap.put(value.code,value);
        }
    }

    public static String getMsg(Integer code){
        return errorEnumMap.get(code).msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
