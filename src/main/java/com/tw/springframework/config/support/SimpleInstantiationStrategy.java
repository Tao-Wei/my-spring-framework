package com.tw.springframework.config.support;

import com.tw.springframework.config.InstantiationStrategy;
import com.tw.springframework.exception.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, Constructor constructor, Object... args) {
        Class beanClass = beanDefinition.getBeanClass();
        try {
            if (constructor != null) {
                return constructor.newInstance(args);
            } else {
                return beanClass.getDeclaredConstructor().newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BeansException();
        }
    }
}
