package com.tw.springframework.pojo;

import com.tw.springframework.config.ConfigurableListableBeanFactory;
import com.tw.springframework.config.support.PropertyValue;
import com.tw.springframework.exception.BeansException;
import com.tw.springframework.lifecycle.BeanFactoryPostProcessor;

public class RenameBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        PropertyValue pv = new PropertyValue();
        pv.setName("name");
        pv.setValue("super 小陶");
        beanFactory.getBeanDefinition("person").getPropertyValues().addPropertyValue(pv);
    }
}
