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
public class StudentList extends AppCompatActivity {
    Button button;
    DatabaseReference mRef;
    ArrayList<String> names = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private ArrayList<Student> mUserList = new ArrayList<>();
    private RecyclerView mRvData;
    private RecyclerView Rview;
    private DisplayStuData allDataAdapter;
    String desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Toolbar toolbar1 = findViewById(R.id.ftoolbar);
        toolbar1.setTitle("Student List");
        toolbar1.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar1.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        button = findViewById(R.id.addstu);
        mRvData = findViewById(R.id.stulistview);
        ((SimpleItemAnimator) mRvData.getItemAnimator()).setSupportsChangeAnimations(false);
        mRvData.setLayoutManager(new LinearLayoutManager(this));

        mRef = FirebaseDatabase.getInstance().getReference("Student");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String lol = ds.getValue().toString();
                    String eg = ds.child("sname").getValue().toString();
                    Log.d("TAG", eg);
                    Student sry = ds.getValue(Student.class);
                    names.add(eg);
                    mUserList.add(sry);
                }
                allDataAdapter = new DisplayStuData(StudentList.this, mUserList);
                mRvData.setAdapter(allDataAdapter);


                Log.d("TAG", "Adapter Set");
                allDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentList.this, AddStudent.class);
                startActivity(i);
            }
        });
    }

    }


    class DisplayStuData extends RecyclerView.Adapter<DisplayStuData.ItemViewHolder> {
        private ArrayList<String> names,filteredDataList;

        public DisplayStuData(ArrayList<String> names)
        {
            this.names = names;
        }

        private ArrayList<Student> mUserLsit = new ArrayList<>();
        private Context mContext;

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist, parent, false);
            final ItemViewHolder holder = new ItemViewHolder(view);
            return new ItemViewHolder(view);

        }

        public DisplayStuData(Context mContext, ArrayList<Student> mUserLsit) {
            this.mContext = mContext;
            this.mUserLsit = mUserLsit;
        }

        public void filterList(ArrayList<String> filterdNames) {
            this.names = filterdNames;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, final int position) {
            final Student stu = mUserLsit.get(position);
            holder.bind(stu);
            Log.d("TAG", "Bind Views");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean expanded = stu.isExpanded();
                    stu.setExpanded(!expanded);
                    notifyItemChanged(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mUserLsit.size();

        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView stuname, sid, classes, spass, sphno, semail, sdate;
            View subItem;

            public ItemViewHolder(View itemView) {
                super(itemView);
                stuname = itemView.findViewById(R.id.studname);
                sid = itemView.findViewById(R.id.uname);
                classes = itemView.findViewById(R.id.classe);
                spass = itemView.findViewById(R.id.upass);
                sphno = itemView.findViewById(R.id.phno);
                semail = itemView.findViewById(R.id.email);
                sdate = itemView.findViewById(R.id.date);
                subItem = itemView.findViewById(R.id.subitem);
                Log.d("TAG", "Get Views");
            }

            private void bind(Student stu) {
                boolean expanded = stu.isExpanded();
                subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
                stuname.setText(stu.sname);
                sid.setText(stu.sid);
                classes.setText(stu.classes);
                spass.setText(stu.spass);
                sphno.setText(stu.sphno);
                semail.setText(stu.semail);
                sdate.setText(stu.sdate);
            }
        }


    }


