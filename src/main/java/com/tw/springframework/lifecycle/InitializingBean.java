package com.tw.springframework.lifecycle;

public interface InitializingBean {
    /**
     * 执行注册的BeanPostProcessor的postProcessBeforeInitialization后，将会调用此方法
     */
    void afterPropertiesSet() throws Exception;
}
