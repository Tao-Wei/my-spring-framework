package com.tw.springframework.aop;

import lombok.Data;
import net.sf.cglib.proxy.MethodProxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 这个类,来包装用户的BeforeAdvice实现类，这个类的intercept方法中，会先调用用户的前置通知实现类。然后在调用原本的方法
 */
@Data
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {
    private MethodBeforeAdvice advice;

    /**
     * invoke 方法会先调用前置通知的逻辑
     * 然后再调用原本的方法体
     *
     * @param methodInvocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //调用用户的前置通知实现类的before方法
        advice.before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        //使用被代理对象调用原本的逻辑
        return methodInvocation.proceed();
    }
}
