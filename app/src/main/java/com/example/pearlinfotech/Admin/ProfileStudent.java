package com.example.pearlinfotech.Admin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileStudent extends AppCompatActivity {
    TextView stuname,sid,spass,sphno,semail,sdate;
    RecyclerView recyclerView;
    ArrayList<String> courseNames = new ArrayList<>();
    String tid;
    MyRecyclerViewAdapter adapter;
    DatabaseReference mRef;
    DatabaseReference dbCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);
        Bundle bundle1 = getIntent().getExtras();
        recyclerView = findViewById(R.id.suclasse);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tid = bundle1.getString("suid");
        dbCourse=FirebaseDatabase.getInstance().getReference("Course/"+tid);
        stuname=findViewById(R.id.suname);
        sid=findViewById(R.id.suid);
        spass=findViewById(R.id.supass);
        sphno=findViewById(R.id.suphno);
        semail=findViewById(R.id.suemail);
        sdate=findViewById(R.id.sudate);
        Toolbar toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle("Student Profile");
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
        mRef = FirebaseDatabase.getInstance().getReference("Student/"+tid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String sname=dataSnapshot.child("sname").getValue().toString();
                    stuname.setText(sname);
                    String sids=dataSnapshot.child("sid").getValue().toString();
                    sid.setText(sids);
                    String s=dataSnapshot.child("spass").getValue().toString();
                    spass.setText(s);
                    String ph=dataSnapshot.child("sphno").getValue().toString();
                    sphno.setText(ph);
                    String email=dataSnapshot.child("semail").getValue().toString();
                    semail.setText(email);
                    String date=dataSnapshot.child("sdate").getValue().toString();
                    sdate.setText(date);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbCourse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String val=ds.getValue().toString();
                    Log.d("TAG",val);
                    courseNames.add(val);
                }
                adapter = new MyRecyclerViewAdapter(ProfileStudent.this, courseNames);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.courselist, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String cname = mData.get(position);
        holder.myTextView.setText(cname);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
