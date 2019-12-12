package com.example.pearlinfotech.ExamDetail;

import android.content.Context;
import android.os.Bundle;
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
    private DisplayAllData allDataAdapter;
    private DatabaseReference mDatabase,mDB;
    private TextView mTvEmpty;
    ArrayList<String> getkey=new ArrayList<>();
    private FirebaseDatabase mFirebaseInstance;
    String examname;
    private ArrayList<Exam> mUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_student);
        Bundle bundle1 = getIntent().getExtras();
        examname = bundle1.getString("ename");
        Toolbar toolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Give Exam Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("Exam");
        mDB=mDatabase.child(examname);
        mRvData = findViewById(R.id.examrcv);
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        Query query=FirebaseDatabase.getInstance().getReference("Fees/"+examname).orderByChild("ename").equalTo(examname);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key=dataSnapshot1.getKey();
                    getkey.add(key);
                    Exam fee = dataSnapshot1.getValue(Exam.class);
                    if (fee.sname.equals(stuname)) {
                        mUserList.add(fee);
                    }
                }
                for (Exam f : mUserList) {
                    if (f.getSName() != null && f.getSName().contains(stuname)) {
                        allDataAdapter = new DisplayAllData(ExamStudent.this, mUserList);
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

class DisplayAllData extends RecyclerView.Adapter<DisplayAllData.ItemViewHolder>
{
    private  ArrayList<Exam> mUserLsit=new ArrayList<>();
    private Context mContext;

    @Override
    public DisplayAllData.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rcvexam,parent,false);
        return new ItemViewHolder(view);
    }


    public DisplayAllData(Context mContext,ArrayList<Exam> mUserLsit) {
        this.mContext=mContext;
        this.mUserLsit = mUserLsit;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Exam exam=mUserLsit.get(position);
        holder.total.setText(String.valueOf(fee.total));
        holder.paid.setText(String.valueOf(fee.paid));
        holder.t1.setText(fee.type1);
        holder.t2.setText(fee.type2);
    }

    @Override
    public int getItemCount() {
        return mUserLsit.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, date, time;
        public ItemViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tv1);
            date=itemView.findViewById(R.id.tv2);
            time=itemView.findViewById(R.id.tv3);

        }
    }
}
