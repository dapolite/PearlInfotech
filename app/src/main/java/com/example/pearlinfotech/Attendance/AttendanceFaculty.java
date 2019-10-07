package com.example.pearlinfotech.Attendance;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pearlinfotech.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class AttendanceFaculty extends AppCompatActivity  {
    RecyclerView recyclerView;
    StudentAdapter adapter;
    FirebaseFirestore db;
    String cors;
    RecyclerView.LayoutManager layoutManager;
    Calendar calendar;
    CollectionReference cRef;
    String date;
    String[] pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_faculty);
        db = FirebaseFirestore.getInstance();
        cRef=db.collection("student");
        pa=getResources().getStringArray(R.array.pa);
        Intent intent = getIntent();
        cors= intent.getStringExtra("COURSE");
        date= intent.getStringExtra("DATE");
        setUpRecyclerView();
    }

    public void setUpRecyclerView(){
        com.google.firebase.firestore.Query query = cRef.whereEqualTo("course_name",cors);
        FirestoreRecyclerOptions<StudentItem> options = new FirestoreRecyclerOptions.Builder<StudentItem>()
                .setQuery(query,StudentItem.class)
                .build();
        adapter = new StudentAdapter(options);
        recyclerView=findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(AttendanceFaculty.this));
        recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int pos) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AttendanceFaculty.this);
                builder.setTitle("Mark Attendance");
                builder.setSingleChoiceItems(pa, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
