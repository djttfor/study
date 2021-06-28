package com.ex.server.websocket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Integer uid;
    private String username;
    private Session session;

}
