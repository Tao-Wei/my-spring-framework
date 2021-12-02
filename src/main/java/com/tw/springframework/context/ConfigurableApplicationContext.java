package com.tw.springframework.context;

import com.tw.springframework.exception.BeansException;

import java.io.IOException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     * @throws BeansException
     */
    void refresh() throws BeansException, IOException, ClassNotFoundException;

    /**
     * 让jvm退出当时候，调用close方法
     */
    void registerShutdownHook();

    /**
     * 关闭容器
     */
    void close();
}
