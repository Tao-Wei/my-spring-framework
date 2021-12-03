package com.tw.springframework.lifecycle;

import cn.hutool.core.util.StrUtil;
import com.tw.springframework.config.support.BeanDefinition;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DisposableBeanAdapter implements DisposableBean{
    private String beanName;
    private Object bean;
    private String destroyMethodName;

    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }
        if (StrUtil.isNotBlank(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(destroyMethodName))) {
           bean.getClass().getMethod(destroyMethodName).invoke(bean);
        }
    }
}
