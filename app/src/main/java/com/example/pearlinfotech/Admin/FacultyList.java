package com.example.pearlinfotech.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FacultyList extends AppCompatActivity {
    Button button;
    DatabaseReference mRef;
    RecyclerView expanderRecyclerView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private ArrayList<Faculty> mUserList = new ArrayList<>();

    private RecyclerView mRvData;
    private DisplayFacData allDataAdapter;
    Faculty fac;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);
        Toolbar toolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("View Faculties");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        button=findViewById(R.id.addfac);
        mRef = FirebaseDatabase.getInstance().getReference("Faculty");
        mRvData = findViewById(R.id.faclist);
        ((SimpleItemAnimator) mRvData.getItemAnimator()).setSupportsChangeAnimations(false);
        mRvData.setLayoutManager(new LinearLayoutManager(this));

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String lol=ds.getValue().toString();
                    Log.d("TAG",lol);
                    Faculty sry = ds.getValue(Faculty.class);
                    mUserList.add(sry);
                }
                allDataAdapter = new DisplayFacData(FacultyList.this, mUserList);
                mRvData.setAdapter(allDataAdapter);
                Log.d("TAG","Adapter Set");
                //allDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FacultyList.this,AddFaculty.class);
                startActivity(i);
            }
        });
    }


}

class DisplayFacData extends RecyclerView.Adapter<DisplayFacData.ItemViewHolder>{
    private  ArrayList<Faculty> mUserLsit=new ArrayList<>();
    private Context mContext;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist,parent,false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        return new ItemViewHolder(view);

    }


    public DisplayFacData(Context mContext,ArrayList<Faculty> mUserLsit) {
        this.mContext=mContext;
        this.mUserLsit = mUserLsit;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder,final int position) {
        final Faculty fac=mUserLsit.get(position);
        holder.bind(fac);
        Log.d("TAG","Bind Views");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean expanded = fac.isExpanded();
                fac.setExpanded(!expanded);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserLsit.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView stuname,sid,classes,spass,sphno,semail,sdate;
        View subItem;
        public ItemViewHolder(View itemView) {
            super(itemView);
            stuname=itemView.findViewById(R.id.studname);
            sid=itemView.findViewById(R.id.uname);
            classes=itemView.findViewById(R.id.classe);
            spass=itemView.findViewById(R.id.upass);
            sphno=itemView.findViewById(R.id.phno);
            semail=itemView.findViewById(R.id.email);
            sdate=itemView.findViewById(R.id.date);
            subItem = itemView.findViewById(R.id.subitem);
            Log.d("TAG","Get Views");
        }

        private void bind(Faculty fac) {
            boolean expanded = fac.isExpanded();
            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
            stuname.setText(fac.tname);
            sid.setText(fac.tid);
            classes.setText(fac.classes);
            spass.setText(fac.tpass);
            sphno.setText(fac.tphno);
            semail.setText(fac.temail);
            sdate.setText(fac.tdate);
        }
    }


}

