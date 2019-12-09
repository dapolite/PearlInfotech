package com.example.pearlinfotech.Admin;

public class Student {
    String sname;
    String sid;
    String classes;
    String spass;
    String sphno;
    String sdate;
    boolean expanded;

    public String getSphno() {
        return sphno;
    }

    public String getSdate() {
        return sdate;
    }

    public String getSemail() {
        return semail;
    }

    String semail;

    Student(){}

    Student(String sname, String sid, String classes, String spass,String semail,String sphno,String sdate) {
        this.sname = sname;
        this.sid = sid;
        this.classes = classes;
        this.spass=spass;
        this.semail=semail;
        this.sdate=sdate;
        this.sphno=sphno;
    }

    public String getSname() {
        return sname;
    }

    public String getSid() {
        return sid;
    }

    public String getClasses() {
        return classes;
    }

    public String getspass() {
        return spass;
    }
    public boolean isExpanded() {
        return expanded;
    }
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}

