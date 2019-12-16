package com.example.pearlinfotech.ExamDetail;

public class Exam
{
    public Exam(){}
    public Exam(String ename, String edate,String etime) {
        this.ename = ename;
        this.edate = edate;
        this.etime = etime;
    }

    String ename;
    String edate;
    String etime;


    public String getEname() {
        return ename;
    }
    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getDate() {
        return edate;
    }
    public void setDate(String date) {
        this.edate = date;
    }

    public String getTime() {
        return etime;
    }
    public void setTime(String etime) {
        this.etime = etime;
    }
}
