package com.example.pearlinfotech.Admin;

public class Faculty {
    String tname;
    String tid;
    String temail;
    String tdate;
    String tphno;
    String classes;

    public String getTemail() {
        return temail;
    }

    public String getTdate() {
        return tdate;
    }

    public String getTphno() {
        return tphno;
    }

    public String getTpass() {
        return tpass;
    }

    String tpass;
    boolean expanded;

    public Faculty(String tname, String tid, String temail, String classes, String tpass,String tdate,String tphno) {
        this.tname = tname;
        this.tid = tid;
        this.classes = classes;
        this.tpass = tpass;
        this.temail=temail;
        this.tdate=tdate;
        this.tphno=tphno;
    }

    public String getTname() {
        return tname;
    }

    public String getTid() {
        return tid;
    }

    public String getClasses() {
        return classes;
    }

    public String gettpass() {
        return tpass;
    }
    public boolean isExpanded() {
        return expanded;
    }
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

}


