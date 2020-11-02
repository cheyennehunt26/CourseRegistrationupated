package com.example.courseregistration;

/// file student package strings
public class Student {
    String name;
    String rollNumber;
    String course;
    Integer priority;
    //setting strings for students
    public Student(String name, String rollNumber, String course, Integer priority) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.course = course;
        this.priority = priority;
    }
    public String getName() {
        return name;
    }
    public String getRollNumber() {
        return rollNumber;
    }
    public String getCourse() {
        return course;
    }
    public Integer getPriority() {
        return priority;
    }
}
