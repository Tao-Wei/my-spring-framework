package com.tw.springframework;

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
    private Object bean;
}
