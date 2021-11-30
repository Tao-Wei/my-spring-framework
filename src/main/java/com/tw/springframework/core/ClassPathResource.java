package com.tw.springframework.core;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类路径资源加载
 */
public class ClassPathResource implements Resource {
    private String path;

    public ClassPathResource(String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return ClassPathResource.class.getClassLoader().getResourceAsStream(path);
    }
}
