package com.tw.springframework.aop;

/**
 * 这个接口继承了Advisor，而且有定义了获取切入点对象的方法
 * 这个接口的实现类，用在配置文件中，定义哪些切入点使用哪些通知
 */
public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
