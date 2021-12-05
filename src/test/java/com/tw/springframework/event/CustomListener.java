package com.tw.springframework.event;


public class CustomListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("收到事件，事件消息为:" + event.getMessage());
    }
}
