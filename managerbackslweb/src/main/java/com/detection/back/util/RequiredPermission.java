package com.detection.back.util;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//贴在方法上
@Retention(RetentionPolicy.RUNTIME)//存活在JVM
public @interface RequiredPermission {
    String value();//表示权限的名称
}
