package com.example.pearlinfotech.Performances;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class Performance_student extends AppCompatActivity {


    Toolbar mToolbar;
    private RecyclerView mRvData;
    private DisplayData allDataAdapter;
    private DatabaseReference mDatabase,mDB;
    private TextView mTvEmpty;
    ArrayList<String> getkey=new ArrayList<>();
    private FirebaseDatabase mFirebaseInstance;
    String stuname;
    private ArrayList<Performance> mUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_student);
        Bundle bundle1 = getIntent().getExtras();
        stuname = bundle1.getString("sname");
        Log.d("TAG",stuname);
        mFirebaseInstance = FirebaseDatabase.getInstance();
       // mDatabase = mFirebaseInstance.getReference("Performance");
        //mDB=mDatabase.child(stuname);
        mRvData = findViewById(R.id.rcvperf);
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        Query query=FirebaseDatabase.getInstance().getReference("Performance/"+stuname).orderByChild("sname").equalTo(stuname);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Performance perf = dataSnapshot1.getValue(Performance.class);
                        mUserList.add(perf);
                    allDataAdapter = new DisplayData(Performance_student.this, mUserList);
                    mRvData.setAdapter(allDataAdapter);
                    allDataAdapter.notifyDataSetChanged();
                }
                for (Performance f : mUserList) {
                    if (f.getPname() != null && f.getPname().contains(stuname)) {


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

class DisplayData extends RecyclerView.Adapter<DisplayData.ItemViewHolder>{
    private ArrayList<Performance> mUserLsit=new ArrayList<>();
    private Context mContext;

    @Override
    public DisplayData.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.perflist,parent,false);
        return new DisplayData.ItemViewHolder(view);
    }


    public DisplayData(Context mContext,ArrayList<Performance> mUserLsit) {
        this.mContext=mContext;
        this.mUserLsit = mUserLsit;
    }

    @Override
    public void onBindViewHolder(DisplayData.ItemViewHolder holder, int position) {
        Performance perf=mUserLsit.get(position);
        holder.sname.setText(perf.tname);
        holder.topic.setText(perf.topic);
        holder.total.setText(String.valueOf(perf.total));
        holder.corr.setText(String.valueOf(perf.correct));
        holder.in.setText(String.valueOf(perf.incorrect));
        //holder.per.setText(String.valueOf(perf.per));
    }

    @Override
    public int getItemCount() {
        return mUserLsit.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView sname,topic,total,corr,in;
        public ItemViewHolder(View itemView) {
            super(itemView);
            sname=itemView.findViewById(R.id.spname);
            topic=itemView.findViewById(R.id.topic);
            total=itemView.findViewById(R.id.total);
            corr=itemView.findViewById(R.id.correct);
            in=itemView.findViewById(R.id.incorrect);
        }
    }


}