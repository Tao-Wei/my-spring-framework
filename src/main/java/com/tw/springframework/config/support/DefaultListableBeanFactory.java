package com.tw.springframework.config.support;

import com.tw.springframework.config.BeanDefinitionRegistry;
import com.tw.springframework.config.ConfigurableListableBeanFactory;
import com.tw.springframework.exception.BeansException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private final Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitions.get(beanName);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitions.forEach((key, value) -> {
            if (value.isSingleton()) {
                getBean(key);
            }
        });
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
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        List<Map.Entry<String, BeanDefinition>> beanDefinitionOfBeanType = getBeanDefinitionOfBeanType(requiredType);
        if (beanDefinitionOfBeanType.size() == 1) {
            return getBean(beanDefinitionOfBeanType.get(0).getKey(), requiredType);
        }
        throw new BeansException(requiredType + "expected single bean but found " + beanDefinitionOfBeanType + ": " + beanDefinitionOfBeanType.stream().map(Map.Entry::getKey).collect(Collectors.toList()));

    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return getBeanDefinitionOfBeanType(clazz).stream().collect(Collectors.toMap(Map.Entry::getKey, x -> getBean(x.getKey(), clazz)));
    }

    private <T> List<Map.Entry<String, BeanDefinition>> getBeanDefinitionOfBeanType(Class<T> clazz) {
        return beanDefinitions.entrySet().stream().filter(x -> clazz.isAssignableFrom(x.getValue().getBeanClass())).collect(Collectors.toList());
    }

}
