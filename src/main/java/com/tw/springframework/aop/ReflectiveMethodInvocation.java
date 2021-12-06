package com.tw.springframework.aop;

import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.InvocationHandler;
import org.aopalliance.intercept.Invocation;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

@AllArgsConstructor
public class ReflectiveMethodInvocation implements MethodInvocation {
    Object target;
    Method method;
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
