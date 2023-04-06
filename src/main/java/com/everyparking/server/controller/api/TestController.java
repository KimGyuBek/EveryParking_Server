package com.everyparking.server.controller.api;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public Student test() {
        Student student = new Student(123, 123, "s1");
        return student;
    }

    @Data
    private static class Student {

        public Student(int studentId, int age, String studentName) {
            this.studentId = studentId;
            this.age = age;
            this.studentName = studentName;
        }

        private int studentId;

        private int age;

        private String studentName;

    }
}
