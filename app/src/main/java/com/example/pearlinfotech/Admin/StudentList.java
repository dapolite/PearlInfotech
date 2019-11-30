package com.example.pearlinfotech.Admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StudentList extends AppCompatActivity {
DatabaseReference mRef;
ListView listView;
ArrayList<String> arrayList=new ArrayList<>();
ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        mRef= FirebaseDatabase.getInstance().getReference("Student");
    }
}
