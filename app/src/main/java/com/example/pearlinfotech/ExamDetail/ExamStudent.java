package com.example.pearlinfotech.ExamDetail;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExamStudent extends AppCompatActivity
{
    private RecyclerView mRvData;
    private DisplayExamData allDataAdapter;
    private DatabaseReference mDatabase,mDB;
    private TextView mTvEmpty;
    private FirebaseDatabase mFirebaseInstance;
    String examname;
    private ArrayList<Exam> mUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_student);
        Toolbar toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle("Exam Details");
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
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("Exam");
        mRvData = findViewById(R.id.examrcv);
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        Query query = FirebaseDatabase.getInstance().getReference("Exam").orderByChild("edate");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for(DataSnapshot ds:dataSnapshot1.getChildren()) {
                        String lol = ds.getValue().toString();
                        Log.d("TAG", lol);
                        Exam exam = ds.getValue(Exam.class);
                        mUserList.add(exam);
                        allDataAdapter = new DisplayExamData(ExamStudent.this, mUserList);
                        mRvData.setAdapter(allDataAdapter);
                        allDataAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    }




class DisplayExamData extends RecyclerView.Adapter<DisplayExamData.ItemViewHolder>
{
    private  ArrayList<Exam> mUserLsit=new ArrayList<>();
    private Context mContext;

    @Override
    public DisplayExamData.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rcvexam,parent,false);
        return new ItemViewHolder(view);
    }


    public DisplayExamData(Context mContext, ArrayList<Exam> mUserLsit) {
        this.mContext=mContext;
        this.mUserLsit = mUserLsit;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Exam exam=mUserLsit.get(position);
        holder.ename.setText(exam.ename);
        holder.edate.setText(exam.edate);
        holder.extime.setText(exam.etime);
    }

    @Override
    public int getItemCount() {
        return mUserLsit.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView ename, edate, extime;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ename=itemView.findViewById(R.id.examnm);
            edate=itemView.findViewById(R.id.examdt);
            extime=itemView.findViewById(R.id.exmtime);

        }
    }
}

