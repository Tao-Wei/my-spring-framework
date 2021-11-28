package com.tw.springframework.support;

import com.tw.springframework.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private final Map<String, Object> singletonBeans = new ConcurrentHashMap<>();

    /**
     * 从单利池中获取bean实例
     *
     * @param name
     * @return
     */
    @Override
    public Object getSingleton(String name) {
        return singletonBeans.get(name);
    }

    /**
     * 添加bean实例到单例池,因为这个功能不打算开放给普通用户,所以设置为protected
     *
     * @param beanName
     * @param bean
     */
    protected void addSingleton(String beanName, Object bean) {
        singletonBeans.put(beanName, bean);
    }

}
