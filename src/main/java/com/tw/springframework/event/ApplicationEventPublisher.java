package com.tw.springframework.event;

/**
 * 事件发布者
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
