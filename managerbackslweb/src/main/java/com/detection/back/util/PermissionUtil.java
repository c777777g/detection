package com.detection.back.util;


import java.lang.reflect.Method;

public class PermissionUtil {
    /**
     * 拼接权限表达式的方法
     *
     * @return 权限表达式
     */
    public static String buildExpression(Method method) {
        //获取当前method的类的全限定名
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        return className + ":" + methodName;
    }
}
