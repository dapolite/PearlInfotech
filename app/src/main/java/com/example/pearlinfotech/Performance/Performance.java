package com.example.pearlinfotech.Performance;

public class Performance
{
    String Sname;
    String topic;
    int correct;
    int total;
    int incorrect;


    public Performance(String sname,String topic, int correct,int total,int incorrect)
    {
        this.Sname= Sname;
        this.topic=topic;
        this.correct=correct;
        this.total=total;
        this.incorrect=incorrect;
    }

    public String getSname()
    {
        return Sname;
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

    public int getIncorrect()
    {
        return incorrect;
    }
}

