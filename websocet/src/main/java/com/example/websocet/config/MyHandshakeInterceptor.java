package com.example.websocet.config;

import entity.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {

    //进入hander之前的拦截
    //若返回flase则不连接
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
           HttpSession session= servletRequest.getServletRequest().getSession(false);

            if(session.getAttribute("loginUser") != null){
                 //获取登录用户
                User loginuser=(User) session.getAttribute("loginUser");
                //将用户放入socket处理器的会话中
                map.put("loginUser",loginuser);
                System.out.println(loginuser.getUserName() + "要建立连接");
            }else{
                System.out.println("你没登录不能连接");
                return  false;
            }

        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}

//    /**
//     * 返回false，则不建立连接
//     * @param serverHttpRequest
//     * @param serverHttpResponse
//     * @param webSocketHandler
//     * @param attributes ;;这个参数是websocket的session
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
//       if(request instanceof ServletServerHttpRequest){
//           ServletServerHttpRequest servletServerHttpRequest =(ServletServerHttpRequest)request;
//        HttpSession session= servletServerHttpRequest.getServletRequest().getSession(false);
//           if(session.getAttribute("logonUser") != null){
//               User loginUser=(User)session.getAttribute("logonUser");
//               attributes.put("logonUser",loginUser);
//           }
//           else{
//               return  false;
//           }
//       }
//
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
//
//    }
//}
