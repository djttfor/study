package com.ex.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("t_mail_log")
@Component
public class MailLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    @TableId(value = "msg_id" ,type = IdType.INPUT)
    private String msgId;

    /**
     * 接收员工id
     */
    private Integer eId;

    /**
     * 状态(0:消息投递中1:投递成功2:投递失败)
     */
    private Integer status;

    /**
     * 路由键
     */
    private String routeKey;

    /**
     * 交换机
     */
    private String exchange;

    /**
     * 重试次数
     */
    private Integer count;

    /**
     * 重试时间
     */
    private LocalDateTime tryTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
