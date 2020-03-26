package com.detection.interfaces.controller.user2tcp;

import com.commonsl.model.User;
import com.detection.interfaces.controller.device2tcp.DeviceSession;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

public class UserSession {
    private final Logger log = Logger.getLogger(DeviceSession.class);
    private final String userName;
    private final IoSession session;
    private  final User user;
    private UserRegistry userRegistry;

    public UserSession(String userName ,IoSession session,User user ,UserRegistry userRegistry){
        this.userName = userName;
        this.session = session;
        this.user = user;
        this.userRegistry = userRegistry;
    }
    public void sendMessage(String message) {
        session.write(message);
        log.info(userName + "-" + ":send message:" + message);
    }

    public String getUserName() {
        return userName;
    }

    public IoSession getSession() {
        return session;
    }

    public User getUser() {
        return user;
    }

    public UserRegistry getUserRegistry() {
        return userRegistry;
    }

    public void setUserRegistry(UserRegistry userRegistry) {
        this.userRegistry = userRegistry;
    }

    /**
     * 关闭，下线
     */
    public void close() {

    }
}