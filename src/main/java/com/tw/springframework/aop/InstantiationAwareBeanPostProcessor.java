package com.tw.springframework.aop;

import com.tw.springframework.config.support.PropertyValues;
import com.tw.springframework.exception.BeansException;
import com.tw.springframework.lifecycle.BeanPostProcessor;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     * 如果这个方法返回的不是null, spring就会用方法的返回值作为bean实例
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * 在 Bean 对象实例化完成后，设置属性操作之前执行此方法。
     * 可以使用这个方法，给bean实例，注入属性
     * 可以用这个方法替换beanDefinition中的PropertyValues.
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;

    default Object getEarlyBeanReference(Object bean, String beanName) {
        return bean;
    }
}
