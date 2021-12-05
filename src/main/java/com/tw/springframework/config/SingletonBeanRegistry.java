package com.tw.springframework.config;

/**
 * 单例bean的缓存池
 */
public interface SingletonBeanRegistry {
    /**
     * 从缓存池中获取单利bean的实例
     *
     * @param name
     * @return
     */
    Object getSingleton(String name);

    void registerSingleton(String beanName, Object singletonObject);
}
