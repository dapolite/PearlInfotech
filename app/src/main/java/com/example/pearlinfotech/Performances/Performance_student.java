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

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;

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
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("View Performance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        holder.total.setInnerText(String.valueOf(perf.total));
        holder.corr.setInnerText(String.valueOf(perf.correct));
        holder.in.setInnerText(String.valueOf(perf.incorrect));
        holder.attmpt.setInnerText(String.valueOf(perf.attempt));
        holder.cmmt.setText(perf.comment);
        //holder.per.setText(String.valueOf(perf.per));
    }

    @Override
    public int getItemCount() {
        return mUserLsit.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView sname,topic,cmmt;
        PieView total,corr,in,attmpt;

        public ItemViewHolder(View itemView) {
            super(itemView);
            sname=itemView.findViewById(R.id.spname);
            topic=itemView.findViewById(R.id.topic);
            total = itemView.findViewById(R.id.pieViewTotal);
            corr= itemView.findViewById(R.id.pieViewCorrect);
            attmpt= itemView.findViewById(R.id.pieViewAttempt);
            cmmt=itemView.findViewById(R.id.cmmt);
            in= itemView.findViewById(R.id.pieViewIncoorect);
            PieAngleAnimation animation1 = new PieAngleAnimation(total);
            animation1.setDuration(5000);
            total.startAnimation(animation1);
            PieAngleAnimation animation2 = new PieAngleAnimation(corr);
            animation2.setDuration(5000);
            corr.startAnimation(animation2);
            PieAngleAnimation animation3 = new PieAngleAnimation(in);
            animation3.setDuration(5000);
            in.startAnimation(animation3);
            PieAngleAnimation animation4 = new PieAngleAnimation(attmpt);
            animation3.setDuration(5000);
            attmpt.startAnimation(animation4);
        }
    }


}