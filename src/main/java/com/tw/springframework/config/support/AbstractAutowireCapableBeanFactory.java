package com.tw.springframework.config.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.tw.springframework.aop.InstantiationAwareBeanPostProcessor;
import com.tw.springframework.aop.ObjectFactory;
import com.tw.springframework.config.InstantiationStrategy;
import com.tw.springframework.exception.BeansException;
import com.tw.springframework.lifecycle.*;

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
        try {
            //调用InstantiationAwareBeanPostProcessor的实现类一个机会,他们去尝试创建对象
            bean = resolveBeforeInstantiation(beanName, beanDefinition);
            if (null != bean) {
                return bean;
            }
            bean = createBeanInstance(beanDefinition, args);
            //提前暴露对象
            if (beanDefinition.isSingleton()) {
                Object finalBean = bean;
               addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, beanDefinition, finalBean));
            }

            //在bean实例创建后，属性注入之前，允许InstantiationAwareBeanPostProcessor 改变beanDefinition的属性值
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
            applyPropertyValues(beanDefinition, bean);
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation " + beanName + " fail");
        }
        //单例的bean对象才加入单例池
        if (beanDefinition.isSingleton()) {
            // 获取代理对象,(因为可能出现循环依赖，导致代理对象其实是在其他bean注入属性的时候创建的)
            bean = getSingleton(beanName);
            registerSingleton(beanName, bean);

            addSingleton(beanName, bean);
        }
        return bean;
    }

    protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean) {
        Object exposedObject = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                exposedObject = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference(exposedObject, beanName);
                if (null == exposedObject) return exposedObject;
            }
        }
        return exposedObject;
    }


    /**
     * 在bean实例被创建后，属性赋值前，允许InstantiationAwareBeanPostProcessor的实例，修改beanDefinition中的属性
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    private void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                PropertyValues propertyValues = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                beanDefinition.setPropertyValues(propertyValues);
            }
        }
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result) return result;
            }
        }
        return null;
    }

    /**
     * @param beanName
     * @param beanDefinition
     * @return
     */
    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (null != bean) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    private Object createBeanInstance(BeanDefinition beanDefinition, Object[] args) {
        Object bean = null;
        for (Constructor declaredConstructor : beanDefinition.getBeanClass().getDeclaredConstructors()) {
            if (declaredConstructor.getParameterTypes().length == args.length) {
                bean = instantiationStrategy.instantiate(beanDefinition, declaredConstructor, args);
            }
        }
        return bean;
    }


    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        //执行初始化方法
        invokeInitMethods(bean, beanDefinition);
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        return applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
    }

    //如果bean有销毁方法，那么注册到，要销毁的bean池
    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (!beanDefinition.isSingleton()) {
            return;
        }
        if (bean instanceof DisposableBean || StrUtil.isNotBlank(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(beanName, bean, beanDefinition.getDestroyMethodName()));
        }

    }

    private void invokeInitMethods(Object bean, BeanDefinition beanDefinition) throws Exception {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotBlank(initMethodName) && !(bean instanceof InitializingBean && "afterPropertiesSet".equals(initMethodName))) {
            beanDefinition.getBeanClass().getMethod(initMethodName).invoke(bean);
        }

    }

    private Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) {
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, beanName);
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
