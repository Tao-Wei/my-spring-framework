package com.tw.springframework.support;

import com.tw.springframework.config.BeanFactory;
import com.tw.springframework.exception.BeansException;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String beanName) {
        //尝试从单例池获取bean
        Object bean = getSingleton(beanName);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition);

    }

    /**
     * 创建bean实例，并且放入单例池
     *
     * @param beanName
     * @param beanDefinition
     */
    protected abstract Object createBean(String beanName, Object beanDefinition);

    /**
     * 从bean定义池中获取bean定义对象
     *
     * @param beanName
     * @return
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;


}
