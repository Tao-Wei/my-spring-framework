package com.tw.springframework.pojo.testanno;

import com.tw.springframework.annotation.Autowired;
import com.tw.springframework.annotation.Component;
import com.tw.springframework.annotation.Value;
import lombok.Data;

@Component
@Data
public class Man {
    @Autowired
    private Cat cat;
    @Value("陶xx")
    private String name;
}
