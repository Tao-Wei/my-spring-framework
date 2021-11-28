package com.tw.springframework.support;

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
}
