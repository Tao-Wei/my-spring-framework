package com.tw.springframework.lifecycle;

public interface BeanNameAware extends Aware {
    void setBeanName(String beanName);
}
