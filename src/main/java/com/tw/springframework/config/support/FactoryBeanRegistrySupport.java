package com.tw.springframework.config.support;

import com.tw.springframework.exception.BeansException;
import com.tw.springframework.lifecycle.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object o = factoryBeanObjectCache.get(beanName);
        return (o != NULL_OBJECT ? o : null);
    }

    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
        if (factory.isSingleton()) {
            Object o = factoryBeanObjectCache.get(beanName);
            if (o == null) {
                o = doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName, o == null ? NULL_OBJECT : o);
            }
            return o == NULL_OBJECT ? null : o;
        }
        return this.doGetObjectFromFactoryBean(factory, beanName);
    }

    private Object doGetObjectFromFactoryBean(FactoryBean factory, String beanName) {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException();
        }
    }

}
