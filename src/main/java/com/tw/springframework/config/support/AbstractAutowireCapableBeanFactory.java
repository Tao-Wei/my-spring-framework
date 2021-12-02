package com.tw.springframework.config.support;

import cn.hutool.core.bean.BeanUtil;
import com.tw.springframework.config.InstantiationStrategy;
import com.tw.springframework.extension.BeanPostProcessor;

import java.lang.reflect.Constructor;
import java.util.List;

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
                bean = initializeBean(beanName, bean, beanDefinition);
                addSingleton(beanName, bean);
            }
        }
        return bean;
    }


    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    private Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) {
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            beanPostProcessor.postProcessAfterInitialization(bean, beanName);
        }
        return bean;
    }

    private Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) {
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            beanPostProcessor.postProcessBeforeInitialization(bean, beanName);
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
                realValue = getBean(beanReference.getBeanName());
            }
            BeanUtil.setProperty(bean, fieldName, realValue);
        }
    }
}
