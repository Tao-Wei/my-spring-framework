package com.tw.springframework.pojo.testcycle;

import com.tw.springframework.annotation.Autowired;
import com.tw.springframework.annotation.Component;
import lombok.Data;

public class TeacherImpl implements Teacher{
    private Student student;

    @Override
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
