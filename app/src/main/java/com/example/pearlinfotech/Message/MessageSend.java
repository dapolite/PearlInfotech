package com.example.pearlinfotech.Message;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pearlinfotech.Fees.Fee;
import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raywenderlich.android.validatetor.ValidateTor;

import java.util.ArrayList;

public class MessageSend extends AppCompatActivity {
DatabaseReference dbMessage;
RecyclerView mRvData;
DisplayTextData allDataAdapter;
ArrayList<Message> mUserList = new ArrayList<>();
ValidateTor validateTor=new ValidateTor();
String text,tid;
Button send;
EditText msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send);
        msg=findViewById(R.id.messagesend);
        Bundle bundle1 = getIntent().getExtras();
        tid = bundle1.getString("tid");
        send=findViewById(R.id.sendButton);
        mRvData = findViewById(R.id.messageRecyclerView);
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        dbMessage=FirebaseDatabase.getInstance().getReference("Message");
        dbMessage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for(DataSnapshot ds:dataSnapshot1.getChildren()) {
                        Message message = ds.getValue(Message.class);
                        mUserList.add(message);
                    }
                }
                allDataAdapter = new DisplayTextData(MessageSend.this, mUserList);
                mRvData.setAdapter(allDataAdapter);
                allDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text=msg.getText().toString();
                if(!validateTor.isEmpty(text)) {
                    Message message = new Message(tid, text);
                    dbMessage.child(tid).push().setValue(message);
                }
            }
        });

    }

}

class DisplayTextData extends RecyclerView.Adapter<DisplayTextData.ItemViewHolder>{
    private ArrayList<Message> mUserLsit=new ArrayList<>();
    private Context mContext;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        return new ItemViewHolder(view);
    }


    public DisplayTextData(Context mContext,ArrayList<Message> mUserLsit) {
        this.mContext=mContext;
        this.mUserLsit = mUserLsit;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder,int position) {
        Message message=mUserLsit.get(position);
        holder.sid.setText(message.id);
        holder.mssg.setText(message.text);
    }

    @Override
    public int getItemCount() {
        return mUserLsit.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView sid,mssg;
        public ItemViewHolder(View itemView) {
            super(itemView);
            sid=itemView.findViewById(R.id.messageTextView);
            mssg=itemView.findViewById(R.id.messengerTextView);
        }
    }


}

