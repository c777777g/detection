package com.detection.back.interceptor;


import com.commonsl.model.User;
import com.commonsl.util.Redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//import com.commonsl.model.Admin;


/**
 * 登陆检查拦截器
 *
 * @author lenovo
 */

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(LoginCheckInterceptor.class);

    @Autowired
    private Redis redis;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String requestMethod = request.getMethod(); // 获取请求方式
        LOG.info(String.format("requestMethod:%s", requestMethod));
        String url = request.getRequestURL().toString();
        System.out.println("url---" + url);
        if (url.contains("AudioBroadcastServer") || url.contains("home") || url.contains("getLogin") || url.contains("welcome") || url.contains("download") || url.contains("register") || url.contains("linkmanRegister") || url.contains("changePassword") || url.contains("findPassword")) {
            return true;
        }

        User u = (User) request.getSession().getAttribute("USER");

//        if (url.contains("deviceMgt")) {
//            redis.set(String.format(RedisKey.HTTPSESSION,  request.getSession().getId()), "deviceMgt");
//        }else {
//            redis.set(String.format(RedisKey.HTTPSESSION,  request.getSession().getId()), "other");
//        }

        if (u == null) {
            response.sendRedirect("/back/");
            return false;
        }
        return true;
    }
}
