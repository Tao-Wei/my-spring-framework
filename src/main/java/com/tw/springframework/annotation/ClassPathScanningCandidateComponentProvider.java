package com.tw.springframework.annotation;

import cn.hutool.core.util.ClassUtil;
import com.tw.springframework.config.support.BeanDefinition;
import com.tw.springframework.config.support.PropertyValues;

import java.util.HashSet;
import java.util.Set;

public class ClassPathScanningCandidateComponentProvider {

    /**
     * 加载包中所有标记了Component的类，并且返回这些类中到配置信息，封装到beanDefinition
     *
     * @param basePackage
     * @return
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        HashSet<BeanDefinition> candidates = new HashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setPropertyValues(new PropertyValues(null));
            beanDefinition.setBeanClass(clazz);
            candidates.add(beanDefinition);
        }
        return candidates;
    }
}
