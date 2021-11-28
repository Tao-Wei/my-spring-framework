package com.tw.springframework.support;

import com.tw.springframework.exception.BeansException;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    /**
     * 创建bena实例，并且放入单利池中
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object bean;
        try {
            bean = beanDefinition.getBeanClass().newInstance();
            addSingleton(beanName, bean);
            return bean;
        } catch (Exception e) {
            throw new BeansException("创建bean对象出现错误");
        }
    }
}
