package com.detection.interfaces.interceptor;

import com.commonsl.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestInterceptor extends HandlerInterceptorAdapter{
    private Logger logger =Logger.getLogger(RequestInterceptor.class);

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {

		String url = request.getRequestURL().toString();
		System.out.println("url---"+ url);
		//以下一直不处理
		if (url.contains("/homeApi") ||url.contains("/login") || url.contains("/home") || url.contains("/payTest") || url.contains("/deviceMgt") || url.contains("/goods")){
			if (!url.endsWith("/addToCart.do")){
				return true;
			}
		}
        return false;
//		//需要验证用户
//		USER user = (USER) request.getSession().getAttribute("user");
//		if (user != null){
//			return true;
//		}else {
//			String token = request.getParameter("token");
//			user = getUserByToken(token);
//			if (user != null){
//				request.getSession().setAttribute("user",user);
//				return true;
//			}else {
//				JsonResult jsonResult = new JsonResult();
//				jsonResult.addErrorCode(ErrorCode.CUSTOM_TOKEN_INVALID);
//				response.setDateHeader("Expires", 0);
//				response.setHeader("Pragma", "no-cache");
//				response.setHeader("Cache-Control", "no-cache");
//				response.setContentType("text/html;charset=utf-8");
//				response.getWriter().write(JsonUtil.toJson(jsonResult));
//				return false;
//			}
//		}

	}



//
//	private USER getUserByToken(String token) {
//		if (StringUtil.isBlank(token)){
//			return null;
//		}
//		Entity.USERCriteria criteria = new Entity.USERCriteria();
//		criteria.setToken(Value.eq(token));
//		User user = userService.selectOne(criteria);
//		if (user == null){
//			return null;
//		}else {
//			return user;
//		}
//	}

}
