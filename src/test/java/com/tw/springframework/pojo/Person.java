package com.tw.springframework.pojo;

import com.tw.springframework.config.BeanFactory;
import com.tw.springframework.context.ApplicationContext;
import com.tw.springframework.lifecycle.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Person implements InitializingBean, DisposableBean, BeanFactoryAware, ApplicationContextAware, BeanNameAware {
    private String name;
    private Integer age;
    private Dog dog;
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;
    private String beanName;

    public Person() {
        System.out.println("person");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("person的destroy被调用");
    }

    public void destroyMethod() throws Exception {
        System.out.println("person的destroyMethod被调用");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("person的afterPropertiesSet被调用");
    }

    public void initMethod() {
        System.out.println("person的initMethod被调用");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
