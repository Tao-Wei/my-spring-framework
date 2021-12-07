package com.tw.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * 这个接口主要是声明了获得通知的方法
 */
public interface Advisor {
    Advice getAdvice();
}
