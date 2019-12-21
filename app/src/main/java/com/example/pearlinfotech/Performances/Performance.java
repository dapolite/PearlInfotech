package com.example.pearlinfotech.Performances;

public class Performance {

    public Performance(){}

    public String getComment() {
        return comment;
    }

    public Performance(String uname,String sname, String topic, int total, int attempt, int correct,int incorrect,int totalm, String comment,String date,String tname)
    {
        this.comment=comment;
        this.uname= uname;
        this.sname= sname;
        this.topic=topic;
        this.date=date;
        this.correct=correct;
        this.total=total;
        this.incorrect=incorrect;
        this.tname=tname;
        this.attempt=attempt;
        this.totalm=totalm;
    }



    String comment;
    String uname;

    public String getUname() {
        return uname;
    }

    public String getSname() {
        return sname;
    }

    public String getDate() {
        return date;
    }

    String sname;
    String tname;
    String topic;
    int correct;
    int total;
    int totalm;
    String date;

    public int getAttempt() {
        return attempt;
    }

    int attempt;
    int incorrect;


    public String getTopic()
    {
        return topic;
    }

    public int getCorrect()
    {
        return correct;
    }

    public int getTotal()
    {
        return total;
    }

    public String getTname() {
        return tname;
    }

    public int getIncorrect()
    {
        return incorrect;
    }

    public int getTotalm()
    {
        return totalm;
    }

}

