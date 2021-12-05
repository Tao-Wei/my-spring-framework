package com.tw.springframework.config;


import com.tw.springframework.lifecycle.BeanPostProcessor;

/**
 * 这个接口的主要作用在于可以提供配置Scope、BeanPostProcessor的功能。方便对容器进行扩展
 */

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory ,SingletonBeanRegistry{

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destroySingletons();
}
