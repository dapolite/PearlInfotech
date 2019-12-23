package com.example.pearlinfotech.Fees;

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

public class student_fees extends AppCompatActivity {
    private RecyclerView mRvData;
    private DisplayAllData allDataAdapter;
    private DatabaseReference mDatabase,mDB;
    private FirebaseDatabase mFirebaseInstance;
    String stuname,sid;
    private ArrayList<Fee> mUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_fees);
        Bundle bundle1 = getIntent().getExtras();
        stuname = bundle1.getString("sname");
        sid=bundle1.getString("sid");
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
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("Fees/"+sid);
        Log.d("TAGS",stuname);
        Log.d("TAGS",sid);
        mDB=mDatabase.child(stuname);
        mRvData = findViewById(R.id.feercv);
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        Query query=FirebaseDatabase.getInstance().getReference("Fees");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            String lol = dataSnapshot.getValue().toString();
                            Log.d("TAGS", lol);
                            Fee fee = dataSnapshot1.getValue(Fee.class);
                            mUserList.add(fee);
                            allDataAdapter = new DisplayAllData(student_fees.this, mUserList);
                            mRvData.setAdapter(allDataAdapter);
                            allDataAdapter.notifyDataSetChanged();
                        }
                    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

class DisplayAllData extends RecyclerView.Adapter<DisplayAllData.ItemViewHolder>{
    private  ArrayList<Fee> mUserLsit=new ArrayList<>();
    private Context mContext;

    @Override
    public DisplayAllData.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rcvfee,parent,false);
        return new ItemViewHolder(view);
    }


    public DisplayAllData(Context mContext,ArrayList<Fee> mUserLsit) {
        this.mContext=mContext;
        this.mUserLsit = mUserLsit;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Fee fee=mUserLsit.get(position);
        holder.subject.setText(fee.course);
        holder.total.setText(String.valueOf(fee.total));
        holder.paid.setText(String.valueOf(fee.paid));
        holder.t1.setText(fee.type1);
        holder.t2.setText(fee.type2);
    }

    @Override
    public int getItemCount() {
        return mUserLsit.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView sname,subject,total,paid,t1,t2;
        public ItemViewHolder(View itemView) {
            super(itemView);
            subject=itemView.findViewById(R.id.sub);
            total=itemView.findViewById(R.id.total);
            paid=itemView.findViewById(R.id.paid);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);

        }
    }


}

