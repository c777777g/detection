package com.detection.back.controller.webSocket;

import com.commonsl.model.User;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketUser {


    private final WebSocketSession webSocketSession;
    private User user;

    public WebSocketUser(User user, WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
        this.user = user;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void sendMessage(String m) {
        try {
            webSocketSession.sendMessage(new TextMessage(m));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}