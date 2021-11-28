package com.tw.springframework.support;

import com.tw.springframework.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private final Map<String, Object> singletonBeans = new ConcurrentHashMap<>();

    @Override
    public Object getSingleton(String name) {
        return singletonBeans.get(name);
    }

    protected void addSingleton(String beanName, Object bean) {
        singletonBeans.put(beanName, bean);
    }

}
