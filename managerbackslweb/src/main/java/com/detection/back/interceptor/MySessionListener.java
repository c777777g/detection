package com.detection.back.interceptor;

import com.commonsl.model.User;
import com.commonsl.service.UserService;
import com.commonsl.util.JsonHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

@Controller
public class MySessionListener implements HttpSessionListener{
    private Logger logger = Logger.getLogger(MySessionListener.class);
    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession hs = se.getSession();
        User u = (User) hs.getAttribute("USER");
        u.setLandingTime(new Date());
        u.setState("离线");
        logger.info("session关闭："+ JsonHelper.toJson(u));
//        userService.update(u);
    }


}

