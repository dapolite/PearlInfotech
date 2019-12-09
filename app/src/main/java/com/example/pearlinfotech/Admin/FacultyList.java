package com.example.pearlinfotech.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

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
        expanderRecyclerView = findViewById(R.id.faclist);
        mRef = FirebaseDatabase.getInstance().getReference("Faculty");


        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                     desc = ds.child("tname").getValue(String.class);
                     arrayList.add(desc);
                }
                adapter.notifyDataSetChanged();
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


