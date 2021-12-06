package com.tw.springframework.aop;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;

@Data
@AllArgsConstructor
public class AdvisedSupport {
    /**
     * 被代理对象
     */
    private Object target;
    /**
     * 方法匹配器，用来判断目标方法，是否和切入点表达式匹配。
     */
    private MethodMatcher methodMatcher;
    /**
     * 方法拦截器，增强的逻辑写在invoke方法。实现类让用户提供，
     */
    private MethodInterceptor methodInterceptor;
}
