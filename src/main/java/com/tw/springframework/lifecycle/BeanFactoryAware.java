package com.tw.springframework.lifecycle;

import com.tw.springframework.config.BeanFactory;

public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory);
}
