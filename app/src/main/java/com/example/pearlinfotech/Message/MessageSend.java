package com.example.pearlinfotech.Message;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.raywenderlich.android.validatetor.ValidateTor;
import com.tfb.fbtoast.FBToast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("MM-dd-yyyy HH:mm a");
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        Toolbar toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle("Fee Details");
        toolbar1.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String localTime = date.format(currentLocalTime);
        dbMessage=FirebaseDatabase.getInstance().getReference("Message");
        Query query=FirebaseDatabase.getInstance().getReference("Message");
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
                FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text=msg.getText().toString();
                if(!validateTor.isEmpty(text)) {
                    Message message = new Message(tid, text,localTime);
                    dbMessage.child(localTime).push().setValue(message);
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
        holder.time.setText(message.time);
    }

    @Override
    public int getItemCount() {
        return mUserLsit.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView sid,mssg,time;
        public ItemViewHolder(View itemView) {
            super(itemView);
            sid=itemView.findViewById(R.id.messageTextView);
            mssg=itemView.findViewById(R.id.messengerTextView);
            time=itemView.findViewById(R.id.timeText);
        }
    }
}