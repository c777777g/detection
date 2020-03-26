package com.commonsl.util;

import javax.servlet.http.HttpServletResponse;


/**
 * 响应上下文工具
 * 
 * @author Jan
 */
public class ResponseContextUtils {

	private static final ThreadLocal<HttpServletResponse> CONTEXT = new ThreadLocal<HttpServletResponse>();
	
	public static void setContext(HttpServletResponse response) {
		CONTEXT.set(response);
	}
	
	/**
	 * 获取当前上下文中的 HttpServletResponse 对象
	 */
	public static HttpServletResponse getContext() {
		return CONTEXT.get();
	}
	
	/**
	 * 序列化对象为JSON串并写出到响应端
	 * 
	 * @param obj
	 *            待序列化的对象
	 */
	public static void writeJson(Object obj) {
		HttpServletResponse response = CONTEXT.get();
		if (response == null) {
			throw new RuntimeException("当前上下文环境中 HttpServletResponse 对象为 null");
		}
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().write(JsonUtil.toJson(obj));
		} catch (IllegalStateException e) {
			
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	
}