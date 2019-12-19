package com.example.pearlinfotech.Message;

import java.util.Date;

public class Message {

   public String id;
   public String text;
   public Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Message(String id, String text, Date time) {
        this.id = id;
        this.text = text;
        this.time=time;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Message() {
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }




}
