package com.tw.springframework.config.support;

import lombok.Data;

/**
 * 代表bean实例的某个字段的命名以及值
 */
@Data
public class PropertyValue {
    private String name;
    private Object value;
}
