package com.example.pearlinfotech.Performances;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;

public class Performance_faculty extends AppCompatActivity {

    EditText spname, total, correct, incorrect;
    EditText tstname,tpic;


    Button btn;
    String SPname,Tname;
    String Topic;
    int Total,Correct,Incorrect;
    DatabaseReference databasePerformance;
    DatabaseReference dbStudent;
    ArrayList<String> snames=new ArrayList<>();
    ArrayList<String> subs=new ArrayList<>();
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_faculty);
        Bundle bundle1 = getIntent().getExtras();
        Topic = bundle1.getString("class_selected");
        databasePerformance = FirebaseDatabase.getInstance().getReference("Performance");
        dbStudent = FirebaseDatabase.getInstance().getReference("Student");
        spname = findViewById(R.id.editText1);
        tstname = findViewById(R.id.tstname);
        total = findViewById(R.id.editText3);
        correct = findViewById(R.id.editText4);
        incorrect = findViewById(R.id.editText5);

        mToolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Performance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void add_performance(View v)
    {
        dbStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SPname = spname.getText().toString();
                Tname=tstname.getText().toString();
                Total = Integer.parseInt(total.getText().toString());
                Correct = Integer.parseInt(correct.getText().toString());
                Incorrect = Integer.parseInt(incorrect.getText().toString());
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String name=dataSnapshot1.child("sname").getValue().toString();
                        snames.add(dataSnapshot1.child("sname").getValue().toString());
                        Log.d("TAG",name);
                    }
                    if (snames.contains(SPname)) {
                        if (!(TextUtils.isEmpty(spname.getText().toString()))) {
                            String id = databasePerformance.push().getKey();
                            Performance performance = new Performance(SPname, Topic, Total, Correct, Incorrect,Tname);
                            databasePerformance.child(SPname).push().setValue(performance);
                            Toast.makeText(getApplicationContext(), "Performance Updated Successfully", Toast.LENGTH_LONG).show();
                        }
                    else {
                    Toast.makeText(getApplicationContext(), "fields cannot be empty", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(Performance_faculty.this, "Student Does Not Exist", Toast.LENGTH_SHORT).show();
            }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        }
    }

