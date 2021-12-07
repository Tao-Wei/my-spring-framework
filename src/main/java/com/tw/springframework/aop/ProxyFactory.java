package com.tw.springframework.aop;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 就是一个简单工厂，根据参数选择，是返回jdk动态代理，还是cglib动态代理
 */
@Data
@AllArgsConstructor
public class ProxyFactory {
    private AdvisedSupport advisedSupport;

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if (advisedSupport.isProxyTargetClass()) {
           return new Cglib2AopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }
}
