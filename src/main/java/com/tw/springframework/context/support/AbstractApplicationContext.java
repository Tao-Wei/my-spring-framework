package com.tw.springframework.context.support;

import com.tw.springframework.config.ConfigurableListableBeanFactory;
import com.tw.springframework.context.ConfigurableApplicationContext;
import com.tw.springframework.core.DefaultResourceLoader;
import com.tw.springframework.event.*;
import com.tw.springframework.exception.BeansException;
import com.tw.springframework.lifecycle.ApplicationContextAwareProcessor;
import com.tw.springframework.lifecycle.BeanFactoryPostProcessor;
import com.tw.springframework.lifecycle.BeanPostProcessor;

import java.util.Collection;
import java.util.Map;

/**
 * 这个类主要当作用就是实现ConfigurableApplicationContext中所有接口当大体逻辑，部分当实现细节交给子类完成（模版方法模式）
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {

        return getBeanFactory().getBean(requiredType);
    }

    private ApplicationEventMulticaster applicationEventMulticaster;
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    @Override
    public void refresh() throws BeansException {
        // 创建BeanFactory实例，并且加载BeanDefinition
        refreshBeanFactory();

        //获取创建好的beanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 调用容器中的beanFactoryPostProcessor实例的postProcessBeanFactory
        invokeBeanFactoryPostProcessors(beanFactory);

        //将beanPostProcessor 注册到BeanFactory中
        registerBeanPostProcessors(beanFactory);

        //初始化事件发布者
        initApplicationEventMulticaster();

        //注册事件监听器
        registerListeners();

        //提前实例化，作用域为singleton的bean实例
        beanFactory.preInstantiateSingletons();

        //发布容器刷新事件
        finishRefresh();
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshEvent(this));
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Collection<BeanPostProcessor> beanPostProcessors = beanFactory.getBeansOfType(BeanPostProcessor.class).values();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    /**
     * 将beanFactory中的
     */
    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    /**
     * 调用容器中的beanFactoryPostProcessor实例的postProcessBeanFactory
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Collection<BeanFactoryPostProcessor> beanFactoryPostProcessors = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values();
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 获取创建BeanFactory实例
     *
     * @return
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 创建BeanFactory实例，并且加载BeanDefinition
     */
    protected abstract void refreshBeanFactory() throws BeansException;


    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        //发布容器关闭事件
        publishEvent(new ContextCloseEvent(this));
        //执行各个bean的生命周期中的销毁方法
        getBeanFactory().destroySingletons();
    }

    /**
     * 发布事件
     * @param event
     */
    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

}
