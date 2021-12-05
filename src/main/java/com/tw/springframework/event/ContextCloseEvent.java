package com.tw.springframework.event;

import com.tw.springframework.context.ApplicationContext;

public class ContextCloseEvent extends ApplicationContextEvent{
    public ContextCloseEvent(ApplicationContext source) {
        super(source);
    }
}
