package com.tw.springframework;


import com.tw.springframework.pojo.Person;
import org.junit.Test;

public class BeanFactoryTest {
    @Test
    public void testBeanFactory() {

        //创建bean容器
        BeanFactory beanFactory = new BeanFactory();
        //创建bean定义对象
        BeanDefinition PersonBeanDefinition = new BeanDefinition(new Person("陶某", 25));

        //将bean定义对象注册到容器中，然后取出
        String beanName = "person";
        beanFactory.registerBeanDefinition(beanName, PersonBeanDefinition);
        System.out.println(beanFactory.getBean(beanName));
    }
}