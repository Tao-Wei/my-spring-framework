package com.tw.springframework;


import com.tw.springframework.config.BeanDefinitionReader;
import com.tw.springframework.pojo.Dog;
import com.tw.springframework.pojo.Person;
import com.tw.springframework.support.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class BeanFactoryTest {
    @Test
    public void testBeanFactory() throws IOException, ClassNotFoundException {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory, null);
        beanDefinitionReader.LoadBeanDefinition("classpath:applicationContext.xml");
        System.out.println(defaultListableBeanFactory.getBean("person"));
        System.out.println(Arrays.toString(defaultListableBeanFactory.getBeanDefinitionNames()));
        System.out.println(defaultListableBeanFactory.getBeansOfType(Person.class));


    }
}