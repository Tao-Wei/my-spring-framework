package com.tw.springframework.aop;

import com.tw.springframework.utils.ClassUtils;
import lombok.Data;

@Data
public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class getTargetClass() {
        if (ClassUtils.isCglibProxyClass(target.getClass())) {
            return target.getClass().getSuperclass();
        }
        return target.getClass();
    }
}
