package com.tw.springframework;


import com.tw.springframework.pojo.Dog;
import com.tw.springframework.pojo.Person;
import com.tw.springframework.support.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class BeanFactoryTest {
    @Test
    public void testBeanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        BeanDefinition dogBeanDefinition = new BeanDefinition(Dog.class, new PropertyValues(Collections.singletonList(new PropertyValue("name", "小黑"))));
        BeanDefinition personBeanDefinition = new BeanDefinition(Person.class, new PropertyValues(Arrays.asList(new PropertyValue("name", "小陶"), new PropertyValue("dog", new BeanReference("dog")))));
        defaultListableBeanFactory.registerBeanDefinition("dog",dogBeanDefinition);
        defaultListableBeanFactory.registerBeanDefinition("person",personBeanDefinition);
        System.out.println(defaultListableBeanFactory.getBean("person"));

    }
}