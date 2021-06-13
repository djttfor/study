### 简介

**WebSocket**是一种在单个[TCP](https://baike.baidu.com/item/TCP)连接上进行[全双工](https://baike.baidu.com/item/全双工)通信的协议。WebSocket通信协议于2011年被[IETF](https://baike.baidu.com/item/IETF)定为标准RFC 6455，并由RFC7936补充规范。WebSocket [API](https://baike.baidu.com/item/API)也被[W3C](https://baike.baidu.com/item/W3C)定为标准。

WebSocket使得客户端和服务器之间的数据交换变得更加简单，允许服务端主动向客户端推送数据。在WebSocket API中，浏览器和服务器只需要完成一次握手，两者之间就直接可以创建持久性的连接，并进行双向数据传输。

### 背景

很多网站为了实现[推送技术](https://baike.baidu.com/item/推送技术)，所用的技术都是[轮询](https://baike.baidu.com/item/轮询)。轮询是在特定的的时间间隔（如每1秒），由浏览器对服务器发出[HTTP请求](https://baike.baidu.com/item/HTTP请求/10882159)，然后由服务器返回最新的数据给客户端的浏览器。这种传统的模式带来很明显的缺点，即浏览器需要不断的向服务器发出请求，然而HTTP请求可能包含较长的[头部](https://baike.baidu.com/item/头部)，其中真正有效的数据可能只是很小的一部分，显然这样会浪费很多的带宽等资源。

而比较新的技术去做轮询的效果是[Comet](https://baike.baidu.com/item/Comet)。这种技术虽然可以双向通信，但依然需要反复发出请求。而且在Comet中，普遍采用的长链接，也会消耗服务器资源。

在这种情况下，[HTML5](https://baike.baidu.com/item/HTML5)定义了WebSocket协议，能更好的节省服务器资源和带宽，并且能够更实时地进行通讯。

### 优点

- 较少的控制开销。在连接创建后，服务器和客户端之间交换数据时，用于协议控制的数据包头部相对较小。在不包含扩展的情况下，对于服务器到客户端的内容，此头部大小只有2至10[字节](https://baike.baidu.com/item/字节)（和数据包长度有关）；对于客户端到服务器的内容，此头部还需要加上额外的4字节的[掩码](https://baike.baidu.com/item/掩码)。相对于HTTP请求每次都要携带完整的头部，此项开销显著减少了。
- 更强的实时性。由于协议是全双工的，所以服务器可以随时主动给客户端下发数据。相对于HTTP请求需要等待客户端发起请求服务端才能响应，延迟明显更少；即使是和Comet等类似的长轮询比较，其也能在短时间内更多次地传递数据。
- 保持连接状态。与HTTP不同的是，Websocket需要先创建连接，这就使得其成为一种有状态的协议，之后通信时可以省略部分状态信息。而HTTP请求可能需要在每个请求都携带状态信息（如身份认证等）。
- 更好的二进制支持。Websocket定义了[二进制](https://baike.baidu.com/item/二进制)帧，相对HTTP，可以更轻松地处理二进制内容。
- 可以支持扩展。Websocket定义了扩展，用户可以扩展协议、实现部分自定义的子协议。如部分浏览器支持[压缩](https://baike.baidu.com/item/压缩)等。
- 更好的压缩效果。相对于[HTTP压缩](https://baike.baidu.com/item/HTTP压缩)，Websocket在适当的扩展支持下，可以沿用之前内容的[上下文](https://baike.baidu.com/item/上下文)，在传递类似的数据时，可以显著地提高压缩率。

### 握手协议

WebSocket 是独立的、创建在 TCP 上的协议。

Websocket 通过[HTTP](https://baike.baidu.com/item/HTTP)/1.1 协议的101状态码进行握手。

### 导包

```xml
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>
```

### 配置

```java
package com.ex.server.config;

import com.ex.server.constant.IConstant;
import com.ex.server.enums.ErrorEnum;
import com.ex.server.exception.BusinessException;
import com.ex.server.util.AuthorizationUtil;
import com.ex.server.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * 添加这个EndPoint,这样在网页可以通过websocket连接上服务
     * 也就是我们配置websocket的服务地址，并且可以指定是否使用socketJs
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 1.将ws/ep路径注册为stomp的端点，用户连接了这个端点就可以进行websocket通讯，支持socketJs
         * 2.setAllowedOrigins("*")允许跨域
         * 3.withSocketJS()支持socketJS
         */
        registry.addEndpoint("/ws/ep").setAllowedOriginPatterns("*").withSockJS();
    }

    /**
     * 输入管道参数配置,获取用户令牌，做认证
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);
                if(StompCommand.CONNECT.equals(accessor.getCommand())){
                    String token = accessor.getFirstNativeHeader("Auth-Token");
                    log.info("Auth-Token:{}",token);
                    if(StringUtils.hasLength(token)){
                        String authToken = token.substring(IConstant.AUTH_TOKEN_PREFIX.length());
                        String username = jwtTokenUtil.getUsernameByToken(authToken);
                        if (StringUtils.hasLength(username)) {
                            AuthorizationUtil.auth(username,null);
                            accessor.setUser(AuthorizationUtil.getAuthenticationToken(username));
                        }else{
                            throw new BusinessException(ErrorEnum.WEB_SOCKET_AUTH_ERROR);
                        }
                    }else{
                        throw new BusinessException(ErrorEnum.WEB_SOCKET_AUTH_ERROR);
                    }
                }
                return message;
            }
        });
    }

    /**
     * 配置消息代理
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //配置代理域，可以配置多个，配置代理目的域前缀为/queue,可以在配置域上向客户端推送消息
        registry.enableSimpleBroker("/queue");
    }
}
```

### 接受消息并分发

```
@Controller
public class WebSocketController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMsg(ChatMsg chatMsg){
        //simpMessagingTemplate.convertAndSend("/queue/chat",chatMsg);
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(),"/queue/chat",chatMsg);
    }
}
```

### 消息实体类

```
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
```

### 前台vue配置

```
npm install sockjs-client
npm install stompjs
```

连接websocket

```js
const actions = {
  //连接websocket
  connect({commit,state}){
    return new Promise((resolve,reject) => {
      if(!state.stomp){
        let stomp = Stomp.over(new SockJs('http://localhost:81/ws/ep'))
        commit('SET_STOMP',stomp)
        let token = 'Bearer' + getToken();//令牌
        //连接服务端
        stomp.connect({'Auth-Token':token},success=>{
          console.log("websocket已建立连接。。。")
          //订阅频道
          stomp.subscribe('/user/queue/chat',msg=>{
            //处理消息
            let message = JSON.parse(msg.body)
            let fromId = message.fromId
            let toId = message.toId
            let sessionKey = generateSessionKey(fromId,toId)
            processMsg(sessionKey,message)
          })
          resolve(stomp)
        },error=>{
          Message.error('websocket连接出错')
          reject()
        })
      }else{
        resolve(state.stomp)
      }
    })
  },
  disconnect({state}){
    if(state.stomp){
      state.stomp.disconnect(()=>{console.log("断开了websocket连接。。。")})
    }
    state.stomp = null
    state.friendList = []
  },
  initData (context) {
    context.commit('INIT_DATA')
  }
}

function processMsg(sessionId,msg){
  //获取消息会话
  //alert(sessionId)
  let session = state.messageMap.get(sessionId)
  if(session){
    session.push(msg)
  }else{
    let newSession = []
    newSession.push(msg)
    state.messageMap.set(sessionId,newSession)
  }
  localStorage.setItem('vue-chat-messageMap',mapToJson(state.messageMap))
}
```

