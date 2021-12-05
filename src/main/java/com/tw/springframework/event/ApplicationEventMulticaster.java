package com.tw.springframework.event;

/**
 * 事件广播器接口
 */
public interface ApplicationEventMulticaster {
    /**
     * 添加时间监听器
     * @param applicationListener
     */
    void addApplicationListener(ApplicationListener applicationListener);

    /**
     * 移除时间监听器
     * @param applicationListener
     */
    void removeApplicationListener(ApplicationListener applicationListener);

    /**
     * 广播事件
     * @param event
     */
    void multicastEvent(ApplicationEvent event);

}
