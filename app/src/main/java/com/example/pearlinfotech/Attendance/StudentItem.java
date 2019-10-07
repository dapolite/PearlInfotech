package com.example.pearlinfotech.Attendance;

public class StudentItem {
     String student_name;
     String course_name;

     public StudentItem(){}

    public StudentItem(String student_name, String course_name) {
        this.student_name = student_name;
        this.course_name = course_name;
    }

    public String getStudent_name() {
        return student_name;
    }

    public String getCourse_name() {
        return course_name;
    }
}
