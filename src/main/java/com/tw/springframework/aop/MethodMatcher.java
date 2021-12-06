package com.tw.springframework.aop;

import java.lang.reflect.Method;

/**
 * 用来判断方法和切入点是否匹配
 */
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);
}
