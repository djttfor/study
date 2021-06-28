package com.ex.server.websocket.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    /**
     * 是否为系统消息
     */
    private boolean sysFlag;
    /**
     * 谁发的
     */
    private Integer fromId;
    /**
     * 发给谁
     */
    private Integer toId;
    /**
     * 发送的消息
     */
    private String content;
    /**
     * 发送时间
     */
    private LocalDateTime date;

    public Message() {
    }


    public Message(boolean sysFlag, Integer fromId, Integer toId, String content) {
        this.sysFlag = sysFlag;
        this.fromId = fromId;
        this.toId = toId;
        this.content = content;
        this.date = LocalDateTime.now();
    }

    public Message(boolean sysFlag, Integer fromId, Integer toId, String content, LocalDateTime date) {
        this.sysFlag = sysFlag;
        this.fromId = fromId;
        this.toId = toId;
        this.content = content;
        this.date = date;
    }
}
