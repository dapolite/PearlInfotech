package com.example.pearlinfotech.Performances;

public class Performance {

    public Performance(){}

    public String getComment() {
        return comment;
    }

    public Performance(String pname, String topic, int correct, int total, int incorrect,int attempt, String tname, String comment)
    {
        this.comment=comment;
        this.pname= pname;
        this.topic=topic;
        this.correct=correct;
        this.total=total;
        this.incorrect=incorrect;
        this.tname=tname;
        this.attempt=attempt;
    }

    String comment;
    String pname;
    String tname;
    String topic;
    int correct;
    int total;

    public int getAttempt() {
        return attempt;
    }

    int attempt;
    int incorrect;

    public String getPname()
    {
        return pname;
    }

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

}

