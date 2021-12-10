package com.tw.springframework.config;

import com.tw.springframework.annotation.StringValueResolver;
import com.tw.springframework.config.support.BeanDefinition;
import com.tw.springframework.exception.BeansException;
import com.tw.springframework.lifecycle.BeanPostProcessor;

public interface ConfigurableListableBeanFactory extends ConfigurableBeanFactory, ListableBeanFactory, AutowireCapableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
