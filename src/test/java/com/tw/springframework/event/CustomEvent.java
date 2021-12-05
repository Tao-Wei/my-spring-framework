package com.tw.springframework.event;

import lombok.Data;

@Data
public class CustomEvent extends ApplicationEvent {
    private String message;

    public CustomEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
