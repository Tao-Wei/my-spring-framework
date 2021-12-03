package com.tw.springframework.pojo;

import com.tw.springframework.exception.BeansException;
import com.tw.springframework.lifecycle.BeanPostProcessor;

public class ModifyAgeBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("person".equals(beanName)) {
            ((Person) bean).setAge(10000);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
