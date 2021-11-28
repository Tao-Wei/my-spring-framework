package com.tw.springframework.config;

import com.tw.springframework.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition definition);
}
