package com.commonsl.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;



public class JsonHelper {
	
	private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);

	private static ObjectMapper mapper;
	static{
		mapper = new ObjectMapper();
		//忽略不存在的属性
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	/**
	 * 
	* @Title: JsonHelper.java
	* @Package com.alliance.util
	* @Description: 把一个对象转成json字符串
	* @author chenwenhao 
	* @date 2017-1-13 上午9:51:18
	 */
	public static String toJson(Object obj){
		String result = null;
		try{
			if(obj!=null){
				result = mapper.writeValueAsString(obj);
			}
			return result;
		}catch (Exception e) {
			log.info("error converting object to json : {}",obj);
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	* @Title: JsonHelper.java
	* @Package com.alliance.util
	* @Description: 通过字符串转成对象
	* @author chenwenhao 
	* @date 2017-1-13 上午10:01:59
	 */
	public static <T> T fromJson(String json , Class<T> clazz){
		T result = null;
		try{
			if(StringUtils.isNotBlank(json)){
				result = mapper.readValue(json, clazz);
			}
			return result;
		}catch (Exception e) {
			log.info("error converting json to object : {}",json);
			e.printStackTrace();
		}
		return null;
	}
}
