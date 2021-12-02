package com.tw.springframework.config;

import com.tw.springframework.config.support.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实例化类的策略接口,交给具体的实现类实现
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition, Constructor constructor, Object... args);
}
