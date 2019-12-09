package com.example.pearlinfotech.Message;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MessageSend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send);

    }

    public void sendMessage(String sender,String reciever,String message){
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Notification");
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("reciver",reciever);
        hashMap.put("message",message);
    }
}
