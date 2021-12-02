package com.tw.springframework.config.support;

import com.tw.springframework.config.BeanDefinitionRegistry;
import com.tw.springframework.config.ConfigurableListableBeanFactory;
import com.tw.springframework.exception.BeansException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private final Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitions.get(beanName);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitions.keySet().forEach(this::getBean);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition definition) {
        beanDefinitions.put(beanName, definition);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitions.keySet().toArray(new String[0]);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return beanDefinitions.entrySet().stream().filter(x -> clazz.isAssignableFrom(x.getValue().getBeanClass())).collect(Collectors.toMap(Map.Entry::getKey, x -> getBean(x.getKey(), clazz)));
    }
}
