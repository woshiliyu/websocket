package com.example.websocet.config;

import com.example.websocet.utils.GsonUtil;
import entity.Message;
import entity.User;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;

import static com.example.websocet.utils.GsonUtil.GsonString;


@Component
public class MyWebSocketHandler implements WebSocketHandler {
    //Set<Map.Entry<Integer, String>> entrySet = map.entrySet();
    //Map<Session, UserInfo> connectmap = new HashMap<>();
    private static final Map<String,WebSocketSession> USER_SOCKETSESSION_MAP  ;

    static {
     USER_SOCKETSESSION_MAP=   new HashMap<String,WebSocketSession>();
    }

    //连接成功后的回调。群聊的时候，有人连接进来了，可以用来提示群里成员有新用户进来了
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

       User loginUser=(User)webSocketSession.getAttributes().get("loginUser");
       USER_SOCKETSESSION_MAP.put(loginUser.getUserId(),webSocketSession);

       Message msg =new Message();
       msg.setText(loginUser.getUserName()+"已上线");
       msg.setTime(new Date());

        Iterator<Map.Entry<String, WebSocketSession>> it = USER_SOCKETSESSION_MAP.entrySet().iterator();
        //将所有在线的人的放入消息对象的List集合中
        while (it.hasNext()) {
            Map.Entry<String, WebSocketSession> entry = it.next();
            System.out.println(((User) entry.getValue().getAttributes().get("loginUser")).getUserName());
            msg.getUserList().add((User) entry.getValue().getAttributes().get("loginUser"));

        }
        TextMessage message = new TextMessage(GsonString(msg));
        sendMessageToAll(message);

    }

    //自己定义的方法
    private void sendMessageToAll(TextMessage message) {
        //Set<Map.Entry<String,WebSocketSession>> entries=USER_SOCKETSESSION_MAP.entrySet();

        Iterator<Map.Entry<String, WebSocketSession>> it = USER_SOCKETSESSION_MAP.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, WebSocketSession> entry = it.next();
            //某用户的webSocketSession
            WebSocketSession webSocketSession= entry.getValue();
            //开启多线程发送消息，效率高
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(webSocketSession.isOpen()) {
                            webSocketSession.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }

    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        if(webSocketMessage.getPayloadLength()==0) return;

        Message msg = GsonUtil.GsonToBean(webSocketMessage.getPayload().toString(),Message.class);

        msg.setTime(new Date());

        String text =msg.getText();

        if(msg.getTo() == null){
            //群发
            sendMessageToAll( new TextMessage(GsonString(msg)));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
