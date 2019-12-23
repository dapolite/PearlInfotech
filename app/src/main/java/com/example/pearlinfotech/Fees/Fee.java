package com.example.pearlinfotech.Fees;

public class Fee {



    public Fee(String sname, long total, long paid, String course,String type1, String type2) {
        this.sname = sname;
        this.total = total;
        this.paid = paid;
        this.course = course;
        this.type1 = type1;
        this.type2 = type2;
    }

    public String course;
    public String sname;
    public long total;
    public long paid;
    public String type1;

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public Fee() {

    }

    public String type2;

    public String getSName() {
        return sname;
    }

    public void setSName(String sname) {
        this.sname = sname;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

}
