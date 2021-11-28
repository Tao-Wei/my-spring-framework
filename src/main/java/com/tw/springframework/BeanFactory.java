package com.tw.springframework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean容器
 */
public class BeanFactory {
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap();

    /**
     * 获取bean的方法
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        return beanDefinitions.get(beanName).getBean();
    }

    /**
     * 注册bean定义对象
     */
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitions.put(beanName, beanDefinition);
    }

}
