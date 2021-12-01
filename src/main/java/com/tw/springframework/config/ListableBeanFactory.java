package com.tw.springframework.config;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {
    /**
     * 获取bean定义对象的名称数组
     *
     * @return
     */
    String[] getBeanDefinitionNames();

    /**
     * 获取和这个类型同源的所有bean定义对象的实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> Map<String, T> getBeansOfType(Class<T> clazz);
}
