package com.tw.springframework.support;

import com.tw.springframework.config.InstantiationStrategy;

import java.lang.reflect.Constructor;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    /**
     * 创建bena实例，并且放入单利池中
     *
     * @param beanName
     * @param beanDefinition
     * @param args
     * @return
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Object bean = null;
        for (Constructor declaredConstructor : beanDefinition.getBeanClass().getDeclaredConstructors()) {
            if (declaredConstructor.getParameterTypes().length == args.length) {
                bean = instantiationStrategy.instantiate(beanDefinition, declaredConstructor, args);
                addSingleton(beanName, bean);
            }
        }
        return bean;
    }
}
