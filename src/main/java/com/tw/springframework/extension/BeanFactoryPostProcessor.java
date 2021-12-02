package com.tw.springframework.extension;

import com.tw.springframework.config.ConfigurableListableBeanFactory;
import com.tw.springframework.exception.BeansException;

public interface BeanFactoryPostProcessor {
    /**
     * 在所有的beanDefinition加载到beanFactory后（还没有bean被实例化），容器会调用该方法，提供修改BeanDefinition的机制
     * @param beanFactory
     * @throws BeansException
     */
    void PostProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
