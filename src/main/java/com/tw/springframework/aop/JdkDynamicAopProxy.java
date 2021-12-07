package com.tw.springframework.aop;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 获取AdvisedSupport对象中，target字段的代理类。
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), advised.getTarget().getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //如果被调用的方法匹配切入点表达式，就交给方法拦截器去调用
        if (advised.getMethodMatcher().matches(method, advised.getTarget().getClass())) {
            return advised.getMethodInterceptor().invoke(new ReflectiveMethodInvocation(advised.getTarget(), method, args));
        }
        return method.invoke(advised.getTarget(), args);
    }
}
