package com.example.pearlinfotech.ExamDetail;

public class Exam
{
    public Exam(String ename, String date,String time) {
        this.ename = ename;
        this.date = date;
        this.time = time;
    }

    String ename;
    String date;
    String time;


    public String getEname() {
        return ename;
    }
    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String ename) {
        this.time = time;
    }
}
