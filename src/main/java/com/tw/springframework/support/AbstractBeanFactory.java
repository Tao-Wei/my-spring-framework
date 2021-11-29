package com.tw.springframework.support;

import com.tw.springframework.config.BeanFactory;
import com.tw.springframework.exception.BeansException;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String beanName) {
        //尝试从单例池获取bean
        return doGetBean(beanName);
    }


    @Override
    public Object getBean(String beanName, Object... args) {
        return doGetBean(beanName,args);
    }


    private Object doGetBean(String beanName, Object... args) {
        Object bean = getSingleton(beanName);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        if (beanDefinition == null) {
            throw new BeansException("bean定义对象不存在");
        }
        return createBean(beanName, beanDefinition, args);
    }

    /**
     * 创建bean实例，并且放入单例池
     *  @param beanName
     * @param beanDefinition
     * @param args
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    /**
     * 从bean定义池中获取bean定义对象
     *
     * @param beanName
     * @return
     */
    public abstract BeanDefinition getBeanDefinition(String beanName);


}
