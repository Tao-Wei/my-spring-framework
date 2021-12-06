package com.tw.springframework.aop;


public interface AopProxy {
    /**
     * 获取代理类对象的接口
     *
     * @return
     */
    Object getProxy();
}
