package com.tw.springframework.config.support;

import com.tw.springframework.aop.ObjectFactory;
import com.tw.springframework.config.SingletonBeanRegistry;
import com.tw.springframework.exception.BeansException;
import com.tw.springframework.lifecycle.DisposableBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {


    protected static final Object NULL_OBJECT = new Object();

    //已经注入属性，并且已经代理过了的对象（如果需要代理的的话）
    private final Map<String, Object> singletonBeans = new ConcurrentHashMap<>();
    //还没有注入属性，已经代理过了的对象（如果需要代理的的话）
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();
    //没有注入属性，包装了一层的未代理对象，
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String, ObjectFactory<?>>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    /**
     * 从单利池中获取bean实例
     *
     * @param name
     * @return
     */
    @Override
    public Object getSingleton(String name) {
        Object singletonObject = null;
        //从一级缓存中获取，如果有直接返回
        singletonObject = singletonBeans.get(name);
        if (singletonObject != null) {
            return singletonObject;
        }
        //从二级缓存中获取，如果有直接返回
        singletonObject = earlySingletonObjects.get(name);
        if (singletonObject != null) {
            return singletonObject;
        }

        //从三级缓存中获取，如果有那么调用getObject方法，获取代理对象。然后将对象放入到二级缓存
        ObjectFactory<?> objectFactory = singletonFactories.remove(name);
        if (objectFactory != null) {
            singletonObject = objectFactory.getObject();
            earlySingletonObjects.put(name, singletonObject);
        }
        return singletonObject;
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

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        addSingleton(beanName, singletonObject);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        this.singletonFactories.put(beanName, singletonFactory);
    }

}
