package com.ex.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * websocket通讯的消息
 */
@Data
@EqualsAndHashCode
public class ChatMsg {
    private String fromId;
    private String from;
    private String toId;
    private String to;
    private String content;
    private LocalDateTime date;
    private String fromNickName;
}
