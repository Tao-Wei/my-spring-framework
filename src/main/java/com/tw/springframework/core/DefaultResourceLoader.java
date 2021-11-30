package com.tw.springframework.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

public class DefaultResourceLoader implements ResourceLoader {
    public static final String CLASS_PATH_PREFIX = "classpath:";

    @Override
    public Resource getResource(String path) {
        if (path.startsWith(CLASS_PATH_PREFIX)) {
            return new ClassPathResource(path.substring(CLASS_PATH_PREFIX.length()));
        }
        try {
            return new UrlResource(path);
        } catch (MalformedURLException e) {
            return new FileSystemResource(path);

        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new BufferedReader(new InputStreamReader(new DefaultResourceLoader().getResource("classpath:test").getInputStream())).readLine());
        System.out.println(new BufferedReader(new InputStreamReader(new DefaultResourceLoader().getResource("/Users/taowei/Desktop/test").getInputStream())).readLine());
        System.out.println(new BufferedReader(new InputStreamReader(new DefaultResourceLoader().getResource("https://www.baidu.com").getInputStream())).readLine());
    }
}
