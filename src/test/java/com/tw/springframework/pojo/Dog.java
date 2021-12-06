package com.tw.springframework.pojo;

import com.sun.istack.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
public class Dog {
    private String name;
    private int age;

    public Dog() {
        System.out.println("new dog");
    }

    public Dog(String name) {
        this.name = name;
    }

}

