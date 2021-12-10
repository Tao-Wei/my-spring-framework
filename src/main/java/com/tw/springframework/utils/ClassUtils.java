package com.tw.springframework.utils;

public class ClassUtils {
    public static boolean isCglibProxyClass(Class clazz) {
        return clazz.getName().contains("$$");
    }
}
