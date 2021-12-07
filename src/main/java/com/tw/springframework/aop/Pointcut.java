package com.tw.springframework.aop;

/**
 * 这个接口用来表示切入点
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
