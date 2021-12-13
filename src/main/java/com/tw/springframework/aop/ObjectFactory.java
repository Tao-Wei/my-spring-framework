package com.tw.springframework.aop;

import com.tw.springframework.exception.BeansException;

public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
