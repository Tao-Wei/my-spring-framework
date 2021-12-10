package com.tw.springframework.config;

import com.tw.springframework.exception.BeansException;

/**
 * 容器接口，主要是定义获取对象实例的功能
 */
public interface BeanFactory {
    Object getBean(String beanName);

    Object getBean(String beanName, Object... args);

    <T> T getBean(String beanName, Class<T> clazz);

    <T> T getBean(Class<T> requiredType) throws BeansException;

}
