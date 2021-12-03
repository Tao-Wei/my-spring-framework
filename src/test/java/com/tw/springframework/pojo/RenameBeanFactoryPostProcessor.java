package com.tw.springframework.pojo;

import com.tw.springframework.config.ConfigurableListableBeanFactory;
import com.tw.springframework.config.support.PropertyValue;
import com.tw.springframework.exception.BeansException;
import com.tw.springframework.lifecycle.BeanFactoryPostProcessor;

public class RenameBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void PostProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.getBeanDefinition("person").getPropertyValues().addPropertyValues(new PropertyValue("name", "suepr 小陶"));
    }
}
