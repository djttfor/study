package com.ex.controller;


import com.ex.entity.ChatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class WebSocketController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/cc")
    public void handleMsg(Principal principal, ChatMsg chatMsg){
        String name = principal.getName();
        chatMsg.setFrom(name);
        chatMsg.setFromNickName("NICK_"+name);
        chatMsg.setDate(LocalDateTime.now());
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(),"/topic/cc",chatMsg);
    }
}
