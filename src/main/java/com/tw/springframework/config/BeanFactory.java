package com.tw.springframework.config;

/**
 * 容器接口，主要是定义获取对象实例的功能
 */
public interface BeanFactory {
    Object getBean(String beanName);

    Object getBean(String beanName, Object... args);

}
