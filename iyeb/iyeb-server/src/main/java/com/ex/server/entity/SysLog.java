package com.ex.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 操作用户
     */
    private String userName;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String method;

    /**
     * 方法参数
     */
    private String param;

    /**
     * 访问时间
     */
    private LocalDateTime visitTime;

    /**
     * 操作时长
     */
    private Long executionTime;

    /**
     * ip
     */
    private String ip;

    /**
     * url
     */
    private String url;

    /**
     * 操作系统及浏览器信息
     */
    private String osAndBrowser;

    /**
     * session id
     */
    private String sessionId;

    /**
     * 请求方式
     */
    private String reqMethod;

    /**
     * 是否是Ajax请求
     */
    private String isAjax;

    /**
     * 操作用户id
     */
    private Integer userId;

    /**
     * 方法返回结果
     */
    private String result;

    /**
     * 异常信息
     */
    private String exception;


}
