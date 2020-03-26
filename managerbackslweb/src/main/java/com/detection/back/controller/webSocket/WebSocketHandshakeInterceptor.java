package com.detection.back.controller.webSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);

    /**
     * 握手前
     *
     * @param request
     * @param response
     * @param webSocketHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        logger.info("握手操作");
//        if (request instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
//            HttpSession session = servletServerHttpRequest.getServletRequest().getSession(false);
//            if (session != null) { //从session中获取当前用户
//                User user = (User) session.getAttribute("user");
//                attributes.put("user", user);
//            }
//        }
        return true;
    }

    /**
     * 握手后
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse
            serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    }
}
