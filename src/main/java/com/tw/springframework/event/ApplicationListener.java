package com.tw.springframework.event;

import java.util.EventListener;

public interface ApplicationListener<T> extends EventListener {

    void onApplicationEvent(T event);
}
