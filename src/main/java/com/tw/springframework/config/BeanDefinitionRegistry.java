package com.tw.springframework.config;

import com.tw.springframework.config.support.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition definition);
}
