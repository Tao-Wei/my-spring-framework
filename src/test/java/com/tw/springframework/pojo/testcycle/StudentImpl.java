package com.tw.springframework.pojo.testcycle;

import com.tw.springframework.annotation.Autowired;
import com.tw.springframework.annotation.Component;
import lombok.Data;

public class StudentImpl implements Student {
    private Teacher teacher;

    @Override
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
