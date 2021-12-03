package com.tw.springframework.lifecycle;

public interface DisposableBean {
    /**
     * 当bean被销毁的时候，将会调用此方法
     */
    void destroy() throws Exception;
}