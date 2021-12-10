package com.tw.springframework.annotation;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import com.tw.springframework.aop.InstantiationAwareBeanPostProcessor;
import com.tw.springframework.config.BeanFactory;
import com.tw.springframework.config.ConfigurableListableBeanFactory;
import com.tw.springframework.config.support.BeanReference;
import com.tw.springframework.config.support.PropertyValue;
import com.tw.springframework.config.support.PropertyValues;
import com.tw.springframework.exception.BeansException;
import com.tw.springframework.lifecycle.BeanFactoryAware;
import com.tw.springframework.utils.ClassUtils;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Value annotation = declaredField.getAnnotation(Value.class);
            if (annotation == null) {
                continue;
            }
            String fieldValue = annotation.value();
            fieldValue = beanFactory.resolveEmbeddedValue(fieldValue);
            String fieldName = declaredField.getName();
            BeanUtil.setFieldValue(bean, fieldName, fieldValue);
        }
        for (Field declaredField : declaredFields) {
            Autowired autowired = declaredField.getAnnotation(Autowired.class);
            if (autowired == null) {
                continue;
            }
            Class<?> dependentType = declaredField.getType();
            String dependBeanName = null;
            Qualifier qualifier = declaredField.getAnnotation(Qualifier.class);
            Object dependentBean = null;
            if (qualifier == null) {
                dependentBean = beanFactory.getBean(dependentType);
            } else {
                beanFactory.getBean(qualifier.value(), dependentType);
            }
            BeanUtil.setFieldValue(bean, declaredField.getName(), dependentBean);
        }

        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
