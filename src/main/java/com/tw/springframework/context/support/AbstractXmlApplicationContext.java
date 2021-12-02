package com.tw.springframework.context.support;

import com.tw.springframework.config.support.DefaultListableBeanFactory;
import com.tw.springframework.config.support.XmlBeanDefinitionReader;

import java.io.IOException;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations) {
            beanDefinitionReader.LoadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
