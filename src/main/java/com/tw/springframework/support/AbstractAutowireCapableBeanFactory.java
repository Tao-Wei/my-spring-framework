package com.tw.springframework.support;

import cn.hutool.core.bean.BeanUtil;
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
                applyPropertyValues(beanDefinition, bean);
                addSingleton(beanName, bean);
            }
        }
        return bean;
    }

    /**
     * 填充bean实例的属性
     *
     * @param beanDefinition
     * @param bean
     */
    private void applyPropertyValues(BeanDefinition beanDefinition, Object bean) {
        for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
            String fieldName = propertyValue.getName();
            Object fieldValue = propertyValue.getValue();
            Object realValue = fieldValue;
            //如果字段的值，指向另外一个bean实例
            if (fieldValue instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) fieldValue;
                realValue=getBean(beanReference.getBeanName());
            }
            BeanUtil.setProperty(bean,fieldName,realValue);
        }
    }
}
