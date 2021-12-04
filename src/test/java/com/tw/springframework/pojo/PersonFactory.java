package com.tw.springframework.pojo;

import com.tw.springframework.lifecycle.FactoryBean;

public class PersonFactory implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
