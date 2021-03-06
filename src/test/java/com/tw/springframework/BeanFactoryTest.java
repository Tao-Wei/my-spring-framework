package com.tw.springframework;


import com.tw.springframework.aop.AspectJExpressionPointcut;
import com.tw.springframework.config.BeanDefinitionReader;
import com.tw.springframework.config.support.DefaultListableBeanFactory;
import com.tw.springframework.config.support.XmlBeanDefinitionReader;
import com.tw.springframework.context.support.ClassPathXmlApplicationContext;
import com.tw.springframework.pojo.*;
import com.tw.springframework.pojo.testcycle.Student;
import com.tw.springframework.pojo.testcycle.StudentImpl;
import com.tw.springframework.pojo.testcycle.Teacher;
import com.tw.springframework.pojo.testcycle.TeacherImpl;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;

public class BeanFactoryTest {
    @Test
    public void testBeanFactory() throws IOException, ClassNotFoundException {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory, null);
        beanDefinitionReader.LoadBeanDefinition("classpath:applicationContext.xml");
        new RenameBeanFactoryPostProcessor().postProcessBeanFactory(defaultListableBeanFactory);
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
        Method helloMethod = personClass.getDeclaredMethod("hello");
        System.out.println(new AspectJExpressionPointcut("execution(* com.tw.springframework.pojo.Person.*(..))").matches(helloMethod, personClass));
    }

    @Test
    public void testAOP() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Object person = classPathXmlApplicationContext.getBean("person");
        System.out.println(person.getClass());
        ((Helloable) person).hello();
    }

    @Test
    public void testAnnoBean() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Object man = classPathXmlApplicationContext.getBean("man");
        System.out.println(man);
    }

    @Test
    public void testCycle() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Student student = (Student) classPathXmlApplicationContext.getBean("student");
        System.out.println(student.getClass());
        System.out.println(student.getTeacher().getClass());
        Teacher teacher = (Teacher) classPathXmlApplicationContext.getBean("teacher");
        System.out.println(student.getTeacher()==teacher);
    }


}