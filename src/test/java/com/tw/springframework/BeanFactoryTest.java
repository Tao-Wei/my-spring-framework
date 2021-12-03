package com.tw.springframework;


import com.tw.springframework.config.BeanDefinitionReader;
import com.tw.springframework.config.BeanFactory;
import com.tw.springframework.config.support.DefaultListableBeanFactory;
import com.tw.springframework.config.support.XmlBeanDefinitionReader;
import com.tw.springframework.context.support.ClassPathXmlApplicationContext;
import com.tw.springframework.pojo.ModifyAgeBeanPostProcessor;
import com.tw.springframework.pojo.Person;
import com.tw.springframework.pojo.RenameBeanFactoryPostProcessor;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class BeanFactoryTest {
    @Test
    public void testBeanFactory() throws IOException, ClassNotFoundException {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory, null);
        beanDefinitionReader.LoadBeanDefinition("classpath:applicationContext.xml");
        new RenameBeanFactoryPostProcessor().PostProcessBeanFactory(defaultListableBeanFactory);
        defaultListableBeanFactory.addBeanPostProcessor(new ModifyAgeBeanPostProcessor());
        Object bean = defaultListableBeanFactory.getBean("person");
        System.out.println(bean);
        defaultListableBeanFactory.destroySingletons();
    }

    @Test
    public void testApplicationContext() throws IOException, ClassNotFoundException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        System.out.println(classPathXmlApplicationContext.getBeansOfType(Person.class));
        classPathXmlApplicationContext.registerShutdownHook();
    }
}