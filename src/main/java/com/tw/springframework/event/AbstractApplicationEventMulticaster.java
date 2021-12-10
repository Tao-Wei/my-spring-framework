package com.tw.springframework.event;

import cn.hutool.core.util.ClassUtil;
import com.tw.springframework.config.BeanFactory;
import com.tw.springframework.exception.BeansException;
import com.tw.springframework.lifecycle.BeanFactoryAware;
import com.tw.springframework.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 这个类的主要作用在于为子类提供了supportsEvent、getApplicationListeners。
 * 子类可以直接调用getApplicationListeners方法，获得可以监听某个事件的监听器集合
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
    public final Set<ApplicationListener> applicationListeners = new LinkedHashSet<>();
    private BeanFactory beanFactory;

    public AbstractApplicationEventMulticaster(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener applicationListener) {
        applicationListeners.add(applicationListener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener applicationListener) {
        applicationListeners.remove(applicationListener);
    }

    /**
     * 获取监听该时事件的监听器集合
     *
     * @return
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        ArrayList<ApplicationListener> res = new ArrayList<>();
        for (ApplicationListener applicationListener : applicationListeners) {
            if (supportEvent(applicationListener, event)) {
                res.add(applicationListener);
            }
        }
        return res;
    }

    /**
     * 判断该监听器是否监听该事件
     *
     * @param listener
     * @param event
     * @return
     */
    private boolean supportEvent(ApplicationListener listener, ApplicationEvent event) {
        Class clazz = listener.getClass();
        //如果包含$$就说明是cglib,要获取其超类的class对象
        if (ClassUtils.isCglibProxyClass(clazz)) {
            clazz = clazz.getSuperclass();
        }
        Type genericInterface = clazz.getGenericInterfaces()[0];
        //获得泛型信息
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        try {
            return Class.forName(actualTypeArgument.getTypeName()).isAssignableFrom(event.getClass());
        } catch (ClassNotFoundException e) {
            throw new BeansException("监听器泛型错误，泛型名：" + actualTypeArgument.getTypeName());
        }
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
