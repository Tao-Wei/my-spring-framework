package com.tw.springframework.event;

import com.tw.springframework.context.ApplicationContext;

public class ContextRefreshEvent extends ApplicationContextEvent{

    public ContextRefreshEvent(ApplicationContext source) {
        super(source);
    }
}
