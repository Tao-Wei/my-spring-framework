package com.tw.springframework.support;

import com.tw.springframework.config.BeanDefinitionReader;
import com.tw.springframework.config.BeanDefinitionRegistry;
import com.tw.springframework.core.DefaultResourceLoader;
import com.tw.springframework.core.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    private BeanDefinitionRegistry beanDefinitionRegistry;
    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry, ResourceLoader resourceLoader) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        this.resourceLoader = resourceLoader == null ? new DefaultResourceLoader() : resourceLoader;
    }

    protected BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return beanDefinitionRegistry;
    }

    protected ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}
