package com.tw.springframework.annotation;

/**
 *
 */
public interface StringValueResolver {
    /**
     * 将字符串中的${xxx.xxx}，从property文件中读取内容，进行替换
     *
     * @param strVal
     * @return
     */
    String resolveStringValue(String strVal);
}
