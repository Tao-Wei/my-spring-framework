package com.tw.springframework.config.support;

import lombok.Data;

@Data
/**
 * 代表bean实例的某个字段的命名以及值
 */
public class PropertyValue {
    private final String name;
    private final Object value;
}
