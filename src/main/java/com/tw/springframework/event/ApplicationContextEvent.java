package com.tw.springframework.event;

import com.tw.springframework.context.ApplicationContext;

public abstract class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(ApplicationContext source) {
        super(source);
    }
}
