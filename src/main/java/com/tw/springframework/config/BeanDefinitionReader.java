package com.tw.springframework.config;

import java.io.IOException;

public interface BeanDefinitionReader {
    /**
     * 解析配置文件，并且封装成beanDefinition,并且注册到beanDefinitionRegistry中
     * @param path
     * @throws IOException
     * @throws ClassNotFoundException
     */
    void LoadBeanDefinition(String path) throws IOException, ClassNotFoundException;
}
