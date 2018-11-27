package com.example.websocet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private  MyHandshakeInterceptor myHandshakeInterceptor;

    @Autowired
    private  MyWebSocketHandler myWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
       //  myWebSocketHandler:消息处理器
        ///'/ws':客户端连接服务端的地址，具体地址是 ws://上下文地址/ws
        webSocketHandlerRegistry.addHandler(myWebSocketHandler,"/ws").addInterceptors(myHandshakeInterceptor);
        /**
         * .withSockJS()是spring用来处理浏览器对websocket兼容性的
         */
        webSocketHandlerRegistry.addHandler(myWebSocketHandler,"/ws/sockjs").addInterceptors(myHandshakeInterceptor).withSockJS();
    }
}
