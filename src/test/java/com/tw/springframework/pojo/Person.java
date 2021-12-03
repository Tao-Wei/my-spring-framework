package com.tw.springframework.pojo;

import com.tw.springframework.lifecycle.DisposableBean;
import com.tw.springframework.lifecycle.InitializingBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements InitializingBean, DisposableBean {
    private String name;
    private Integer age;
    private Dog dog;

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
}
