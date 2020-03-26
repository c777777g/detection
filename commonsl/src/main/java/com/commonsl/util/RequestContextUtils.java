package com.commonsl.util;

//import com.commonsl.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 请求上下文工具
 *
 */
public class RequestContextUtils {
	
	private static final ThreadLocal<HttpServletRequest> CONTEXT = new ThreadLocal<HttpServletRequest>();
	
	
	public static void setContext(HttpServletRequest request) {
		CONTEXT.set(request);
	}
	
	/**
	 * 获取当前上下文中的 HttpServletRequest 对象
	 */
	public static HttpServletRequest getContext() {
		return CONTEXT.get();
	}
	
	/**
	 * 获取当前的会话对象
	 */
	public static HttpSession getSession() {
		HttpServletRequest request = CONTEXT.get();
		if (request != null) {
			return request.getSession();
		}
		return null;
	}
	
	
	/**
	 * 获取请求的版本信息, APP 可用
	 */
	public static String getCurrentVersion() {
		HttpServletRequest request = CONTEXT.get();
		return (String) request.getAttribute("VERSION");
	}
	
	/**
	 * 获取请求的终端系统信息, APP 可用
	 */
	public static String getCurrentTerminalSystem() {
		HttpServletRequest request = CONTEXT.get();
		return (String) request.getAttribute("TERMINAL_SYSTEM");
	}
	
//	/**
//	 * 获取 Session 中的用户, WEB 可用
//	 */
//	public static User getCurrentUser() {
//		HttpSession session = getSession();
//		if (session != null) {
//			Object user = session.getAttribute("USER");
//			if (user != null) {
//				return (User) user;
//			}
//		}
//		return null;
//	}
	
	public static Integer getCurrentUserCode() {
		HttpServletRequest request = CONTEXT.get();
		return Integer.valueOf(request.getAttribute("userCode").toString());
	}
	
	
	
	
	/**
	 * 获取服务上下文路径
	 */
	public static String getContextPath() {
		HttpServletRequest request = CONTEXT.get();
		String servletPath = request.getServletPath();
		String requestUrl = request.getRequestURL().toString();
		return requestUrl.substring(0, requestUrl.lastIndexOf(servletPath));
	}
	
	public static String getCurrentUrl() {
		return CONTEXT.get().getRequestURL().toString();
	}
	
}
