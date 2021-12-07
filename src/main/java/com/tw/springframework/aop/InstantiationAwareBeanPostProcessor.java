package com.tw.springframework.aop;

import com.tw.springframework.exception.BeansException;
import com.tw.springframework.lifecycle.BeanPostProcessor;

/**
 * BeanPostProcessor 的字接口。
 * 在beanFactory创建对象前，将会给InstantiationAwareBeanPostProcessor的实现类一个机会。如果实现类返回类对象实例，就不会再去创建对象，而是使用返回的实例。
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
