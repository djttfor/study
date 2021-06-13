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
