package com.tw.springframework;


import com.tw.springframework.aop.AdvisedSupport;
import com.tw.springframework.aop.AspectJExpressionPointcut;
import com.tw.springframework.aop.Cglib2AopProxy;
import com.tw.springframework.aop.JdkDynamicAopProxy;
import com.tw.springframework.config.BeanDefinitionReader;
import com.tw.springframework.config.BeanFactory;
import com.tw.springframework.config.support.DefaultListableBeanFactory;
import com.tw.springframework.config.support.XmlBeanDefinitionReader;
import com.tw.springframework.context.support.ClassPathXmlApplicationContext;
import com.tw.springframework.event.CustomEvent;
import com.tw.springframework.pojo.*;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Period;
import java.util.Arrays;
import java.util.Map;

public class BeanFactoryTest {
    @Test
    public void testBeanFactory() throws IOException, ClassNotFoundException {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory, null);
        beanDefinitionReader.LoadBeanDefinition("classpath:applicationContext.xml");
        new RenameBeanFactoryPostProcessor().PostProcessBeanFactory(defaultListableBeanFactory);
        defaultListableBeanFactory.addBeanPostProcessor(new ModifyAgeBeanPostProcessor());
        Person person = defaultListableBeanFactory.getBean("person", Person.class);
        System.out.println(person);

        System.out.println(person.getBeanFactory() == defaultListableBeanFactory);
        System.out.println(person.getBeanName());

    }

    @Test
    public void testApplicationContext() throws IOException, ClassNotFoundException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        classPathXmlApplicationContext.registerShutdownHook();
    }

    @Test
    public void testMethodMatcher() throws NoSuchMethodException {
        Class<Person> personClass = Person.class;
        Method helloMethod= personClass.getDeclaredMethod("hello");
        System.out.println(new AspectJExpressionPointcut("execution(* com.tw.springframework.pojo.Person.*(..))").matches(helloMethod, personClass));
    }

    @Test
    public void testAOP() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Object person = classPathXmlApplicationContext.getBean("person");
        ((Person)person).hello();
    }
}