package com.commonsl.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpUtil {
	
	
	 /** 
     * 发起get请求 
     *  
     * @param url 
     * @return 
     */  
    public static String httpGet(String url) {  
        String result = null;  
        OkHttpClient client = new OkHttpClient();  
        Request request = new Request.Builder().url(url).build();  
        try {  
            Response response = client.newCall(request).execute();  
            result = response.body().string();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
  
    /** 
     * 发送httppost请求 
     *  
     * @param url 
     * @param data  提交的参数为key=value&key1=value1的形式 
     * @return 
     */  
    public static String httpPost(String url, String data) {  
        String result = null;  
        OkHttpClient httpClient = new OkHttpClient();  
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/html;charset=utf-8"), data);  
        Request request = new Request.Builder().url(url).post(requestBody).build();  
        try {  
            Response response = httpClient.newCall(request).execute();  
            result = response.body().string();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    
    
	 /** 
     * 发起get请求 
     *  
     * @param url 
     * @return 
     */  
    public static byte[] httpGetBytes(String url) {  
        byte [] result = null;  
        OkHttpClient client = new OkHttpClient();  
        Request request = new Request.Builder().url(url).build();  
        try {  
            Response response = client.newCall(request).execute();  
            result = response.body().bytes(); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    
    public static String getIpAddress(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
    
}
