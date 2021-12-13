package com.tw.springframework.config.support;

import cn.hutool.core.util.StrUtil;
import com.sun.deploy.util.StringUtils;
import com.tw.springframework.config.ConfigurableBeanFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * bean定义对象
 */
@Data
@NoArgsConstructor
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

    private boolean singleton = true;
    private boolean prototype = false;

    /**
     * 对象作用域
     */
    private String scope;

    public BeanDefinition(Class beanClass, PropertyValues propertyValues, String initMethodName, String destroyMethodName) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues;
        this.initMethodName = initMethodName;
        this.destroyMethodName = destroyMethodName;
    }

    public void setScope(String scope) {
        singleton = StrUtil.EMPTY.equals(scope)|| ConfigurableBeanFactory.SCOPE_SINGLETON.equals(scope);
        prototype = ConfigurableBeanFactory.SCOPE_PROTOTYPE.equals(scope);
        this.scope = scope;
    }
}
