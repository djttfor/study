package com.ex.server.websocket.endpoint;

import com.ex.server.constant.IConstant;
import com.ex.server.enums.ErrorEnum;
import com.ex.server.exception.BusinessException;
import com.ex.server.util.JsonUtil;
import com.ex.server.util.JwtTokenUtil;
import com.ex.server.websocket.config.CustomSpringConfigurator;
import com.ex.server.websocket.entity.Message;
import com.ex.server.websocket.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/rawChat/{uid}/{token}",configurator = CustomSpringConfigurator.class)
@Component
@Slf4j
public class RawChatEndpoint {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    //在线列表
    private static final Map<String, UserInfo> clientMap = new ConcurrentHashMap<>();

    //用户列表
    private static final Map<Integer, UserInfo> userMap = new ConcurrentHashMap<>();

    //模拟用户列表
    static {
        userMap.put(1,new UserInfo(1,"admin",null));
        userMap.put(2,new UserInfo(2,"mornd",null));
        userMap.put(3,new UserInfo(3,"Anna",null));
        userMap.put(4,new UserInfo(4,"Alice",null));
        userMap.put(5,new UserInfo(5,"贝西",null));
        userMap.put(6,new UserInfo(6,"鲍勃",null));
        userMap.put(7,new UserInfo(7,"凯瑟琳",null));
        userMap.put(8,new UserInfo(8,"黛娜",null));
        userMap.put(9,new UserInfo(9,"艾米莉",null));
        userMap.put(10,new UserInfo(10,"简",null));
        userMap.put(11,new UserInfo(11,"路西",null));
    }

    @OnOpen
    public void onOpen(@PathParam("uid")Integer uid,@PathParam("token")String token, Session session){
        String username = jwtTokenUtil.getUsernameByToken(token.substring(IConstant.AUTH_TOKEN_PREFIX.length()));
        if(null == username){
            throw new BusinessException(ErrorEnum.WEB_SOCKET_AUTH_ERROR);
        }
        //上线
        log.info("用户{}已上线。。。",username);
        clientMap.put(session.getId(),new UserInfo(uid,username,session));
        //记录用户会话
        UserInfo userInfo = userMap.get(uid);
        userInfo.setSession(session);
        //登录成功的系统消息
        sendMessage(session,new Message(true,null,null,"登录成功，欢迎回来"));

        //发布上线通知给所有的好友
        sendSystemMsg(new Message(true,uid,null,"您的好友"+username+"上线了"));
    }
    @OnMessage
    public void onMessage(String message, Session session){
        UserInfo userInfo = clientMap.get(session.getId());
        if(userInfo==null){
            throw new BusinessException(ErrorEnum.WEB_SOCKET_AUTH_ERROR);
        }
        Message sendData = JsonUtil.parseObject(message, Message.class);
        //获得好友id，拿到好友会话
        UserInfo friend = userMap.get(sendData.getToId());
        Session friendSession = friend.getSession();

        //发送消息
        sendMessage(friendSession,new Message(false,sendData.getFromId()
                ,sendData.getToId(),sendData.getContent(),sendData.getDate()));
    }
    @OnClose
    public void onClose(Session session,CloseReason closeReason){
        UserInfo userInfo = clientMap.get(session.getId());
        String username = userInfo.getUsername();
        log.info("用户{}已下线。。。",username);
        clientMap.remove(session.getId());
        //下线通知
        sendSystemMsg(new Message(true,userInfo.getUid(),null,"用户"+username+"下线了"));
    }
    @OnError
    public void onError(Session session,Throwable throwable){
        log.error("Websocket异常：",throwable);
    }

    /**
     * 发送系统消息
     * @param message 消息
     */
    public synchronized void sendSystemMsg(Message message){
        //假设所有人都是自己的好友
        userMap.forEach((k,v)->{
            try {
                if (null != v.getSession() && v.getSession().isOpen() && !(message.getFromId().equals(v.getUid()))) {
                    v.getSession().getBasicRemote().sendText(JsonUtil.toJsonString(message));
                }
            } catch (Exception e) {
                throw new BusinessException("websocket发送消息失败",e);
            }
        });
    }

    /**
     * 发送消息
     * @param session 会话
     * @param message 消息
     */
    public synchronized void sendMessage(Session session, Message message){
        try {
            session.getBasicRemote().sendText(JsonUtil.toJsonString(message));
        } catch (Exception e) {
            throw new BusinessException("websocket发送消息失败",e);
        }
    }
}
