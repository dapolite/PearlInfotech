package com.example.pearlinfotech.ExamDetail;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raywenderlich.android.validatetor.ValidateTor;

import java.util.ArrayList;

public class ExamFaculty extends AppCompatActivity
{
    String ename,date,time;
    ArrayList<String> enames=new ArrayList<>();
    EditText e1,e2,e3;
    Toolbar mToolbar;
    DatabaseReference databaseExam;
    DatabaseReference dbStudent;
    boolean failFlag=false;
    ValidateTor validateTor = new ValidateTor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_faculty);

        e1=findViewById(R.id.exmname);
        e2=findViewById(R.id.dateexam);
        e3=findViewById(R.id.timeexam);


        databaseExam = FirebaseDatabase.getInstance().getReference("Exam");
        dbStudent = FirebaseDatabase.getInstance().getReference("Student");
        mToolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Exam Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    public void addExamDetail(View v) {
        ename = e1.getText().toString();
        date=e2.getText().toString();
        time=e3.getText().toString();
        dbStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    enames.add(dataSnapshot1.child("enmae").getValue().toString());
                }
                if (!validateTor.isEmpty(ename)) {
                    failFlag=true;
                    e1.setError("Field is empty!");
                }
                if (!validateTor.isEmpty(date)) {
                    failFlag=true;
                    e2.setError("Field is empty!");
                }
                if (!validateTor.isInteger(time)) {
                    failFlag=true;
                    e3.setError("Field is empty!");
                }
                if(failFlag=false)
                {
                    String id = databaseExam.push().getKey();
                    Exam exam = new Exam(ename,date,time);
                    databaseExam.child(ename).push().setValue(exam);
                    Toast.makeText(getApplicationContext(), "Exam Details added successfully", Toast.LENGTH_LONG).show();


                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
