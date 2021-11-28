package com.tw.springframework.exception;

/**
 *
 */
public class BeansException extends RuntimeException{
    public BeansException() {
    }

    public BeansException(String message) {
        super(message);
    }
}
