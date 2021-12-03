package com.tw.springframework.lifecycle;

import com.tw.springframework.context.ApplicationContext;

public interface ApplicationContextAware extends Aware{
    void setApplicationContext(ApplicationContext applicationContext);
}
