package com.tw.springframework.config.support;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * bean定义对象
 */
@Data
@AllArgsConstructor
public class BeanDefinition {
    /**
     * bean 对象实例
     */
    private Class beanClass;

    private PropertyValues propertyValues;

    /**
     * xml配置文件中配置的init-method值
     */
    private String initMethodName;
    /**
     * xml配置文件中配置的destroy-method值
     */
    private String destroyMethodName;

}
