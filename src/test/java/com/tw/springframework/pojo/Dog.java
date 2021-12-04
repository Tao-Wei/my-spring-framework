package com.tw.springframework.pojo;

import com.sun.istack.internal.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Dog {
    private String name;

    public Dog() {
        System.out.println("new dog");
    }

    public Dog(String name) {
        this.name = name;
    }
}
