package com.tw.springframework.aop;

/**
 * 类过滤器，用于判断目标类，是否和切入点匹配
 */
public interface ClassFilter {
    /**
     * 判断目标类，是否和切入点匹配
     * @param clazz
     * @return
     */
    boolean matches(Class clazz);
}
