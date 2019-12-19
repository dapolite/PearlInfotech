package com.example.pearlinfotech.Message;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tfb.fbtoast.FBToast;

import java.util.ArrayList;

public class MessageRecieve extends AppCompatActivity {
    DatabaseReference dbMessage;
    RecyclerView mRvData;
    DisplayTextData allDataAdapter;
    ArrayList<Message> mUserList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_recieve);
        mRvData = findViewById(R.id.messageSendRecyclerView);
        mRvData.setLayoutManager(new LinearLayoutManager(MessageRecieve.this));
        dbMessage= FirebaseDatabase.getInstance().getReference("Message");
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
                allDataAdapter = new DisplayTextData(MessageRecieve.this, mUserList);
                mRvData.setAdapter(allDataAdapter);
                allDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);
            }
        });
    }
}

class DisplayMsgData extends RecyclerView.Adapter<DisplayMsgData.ItemViewHolder>{
    private ArrayList<Message> mUserLsit=new ArrayList<>();
    private Context mContext;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        return new ItemViewHolder(view);
    }


    public DisplayMsgData(Context mContext,ArrayList<Message> mUserLsit) {
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