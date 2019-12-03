package com.example.pearlinfotech.Performances;

public class Performance {

    public Performance(){}
    public Performance(String pname,String topic,int correct,int total,int incorrect,String tname)
    {
        this.pname= pname;
        this.topic=topic;
        this.correct=correct;
        this.total=total;
        this.incorrect=incorrect;
        this.tname=tname;
    }

    String pname;
    String tname;
    String topic;
    int correct;
    int total;
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

