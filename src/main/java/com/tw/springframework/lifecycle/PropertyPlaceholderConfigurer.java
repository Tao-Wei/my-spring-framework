package com.tw.springframework.lifecycle;

import com.tw.springframework.config.ConfigurableListableBeanFactory;
import com.tw.springframework.config.support.BeanDefinition;
import com.tw.springframework.config.support.PropertyValue;
import com.tw.springframework.config.support.PropertyValues;
import com.tw.springframework.core.DefaultResourceLoader;
import com.tw.springframework.core.Resource;
import com.tw.springframework.exception.BeansException;

import java.io.IOException;
import java.util.Properties;

/**
 * BeanFactoryPostProcessor的实习类，他的作用就是在beanFactory已经注册了beanDefinition后，依次查看是否那个bean中，含有占位符。
 * 如果有占位符，就使用property配置文件中值进行替换
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 加载属性文件
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)) continue;
                    String strVal = (String) value;
                    StringBuilder buffer = new StringBuilder(strVal);
                    int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
                        String propKey = strVal.substring(startIdx + 2, stopIdx);
                        String propVal = properties.getProperty(propKey);
                        buffer.replace(startIdx, stopIdx + 1, propVal);
                        propertyValue.setValue(buffer.toString());
                    }
                }
            }
        } catch (IOException e) {
            throw new BeansException("Could not load properties");
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
