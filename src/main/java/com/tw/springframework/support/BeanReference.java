package com.tw.springframework.support;

import lombok.Data;

@Data

public class BeanReference {
    private String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }
}
