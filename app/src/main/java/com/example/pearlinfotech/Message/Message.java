package com.example.pearlinfotech.Message;

public class Message {

   public String id;
   public String text;
   public String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Message(String id, String text, String time) {
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
