package com.tw.springframework;


import com.tw.springframework.config.BeanFactory;
import com.tw.springframework.pojo.Person;
import com.tw.springframework.support.BeanDefinition;
import com.tw.springframework.support.DefaultListableBeanFactory;
import org.junit.Test;

public class BeanFactoryTest {
    @Test
    public void testBeanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition("person", new BeanDefinition(Person.class));
        Object person = defaultListableBeanFactory.getBean("person");
        System.out.println(person==defaultListableBeanFactory.getBean("person"));
    }
}