package com.example.pearlinfotech.Admin;

public class Student {
    public String sname;
    public String sid;
    public String classes;
    public String spass;
    public String sphno;
    public String sdate;
    public boolean expanded;

    public String getSphno() {
        return sphno;
    }

    public String getSdate() {
        return sdate;
    }

    public String getSemail() {
        return semail;
    }

    public String semail;

    public Student(){}

    public Student(String sname, String sid, String classes, String spass,String semail,String sphno,String sdate) {
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

