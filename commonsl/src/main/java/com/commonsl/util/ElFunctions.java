package com.commonsl.util;

import net.sf.json.JSONObject;

public class ElFunctions {

    public static String toJsonString(Object obj){
        // 将java对象转换为json对象
        JSONObject json = JSONObject.fromObject(obj);
        String str = json.toString();
        return str;
    }
}