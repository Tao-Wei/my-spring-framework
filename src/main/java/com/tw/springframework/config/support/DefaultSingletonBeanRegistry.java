package com.tw.springframework.config.support;

import com.tw.springframework.config.SingletonBeanRegistry;
import com.tw.springframework.exception.BeansException;
import com.tw.springframework.extension.DisposableBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private final Map<String, Object> singletonBeans = new ConcurrentHashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

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

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException();
            }
        }
    }

}
