package com.tw.springframework.aop;

import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.InvocationHandler;
import org.aopalliance.intercept.Invocation;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * 这个类是用来包装类，用来包装被代理对象
 * 调用方法时候传入的参数的
 * 被调用方法的反射对象
 *
 */
@AllArgsConstructor
public class ReflectiveMethodInvocation implements MethodInvocation {
    /**
     * 被代理对象
     */
    Object target;

    /**
     * 被调用方法的反射对象
     */
    Method method;
    /**
     * 调用方法时候传入的参数
     */
    Object[] args;


    @Override
    public Object[] getArguments() {
        return args;
    }

    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target, args);
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }

    @Override
    public Method getMethod() {
        return method;
    }
}
