package com.tw.springframework.aop;

import lombok.Data;
import org.aopalliance.aop.Advice;

/**
 * 用来封装切入点和通知。
 * 用户在配置文件中，配置的就是这个对象
 */


public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    /**
     * 通知
     */
    private Advice advice;
    /**
     * 切面
     */
    private AspectJExpressionPointcut pointcut;

    /**
     * aspectj的切入点表达式
     */
    private String expression;

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        return new AspectJExpressionPointcut(expression);
    }
}
