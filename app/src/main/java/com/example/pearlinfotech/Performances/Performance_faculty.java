package com.example.pearlinfotech.Performances;

import android.os.Bundle;
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
import com.raywenderlich.android.validatetor.ValidateTor;
import com.tfb.fbtoast.FBToast;

import java.util.ArrayList;

public class Performance_faculty extends AppCompatActivity {

    EditText spname, total, correct, incorrect,sid,totalmarks;
    EditText tstname,tpic,attmpt,comm;
    ValidateTor validateTor = new ValidateTor();
    String class_selected,suname;
    Button btn;
    String SPname,Tname,attpt,cmmt;
    String topic;
    int Total,Correct,Incorrect,Attempted,Totalmarks;
    DatabaseReference databasePerformance;
    DatabaseReference dbStudent;
    ArrayList<String> snames=new ArrayList<>();
    ArrayList<String> subs=new ArrayList<>();
    boolean failFlag=false;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_faculty);
        Bundle bundle1 = getIntent().getExtras();
        class_selected   = bundle1.getString("class");
        Log.d("TAG",class_selected );
        Toolbar toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle("Performance Details");
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
        databasePerformance = FirebaseDatabase.getInstance().getReference("Performance");
        dbStudent = FirebaseDatabase.getInstance().getReference("Student");
        sid=findViewById(R.id.editTextstid);
        spname = findViewById(R.id.editText1);
        tstname = findViewById(R.id.tstname);
        total = findViewById(R.id.editText3);
        correct = findViewById(R.id.editText4);
        incorrect = findViewById(R.id.editText5);
        totalmarks = findViewById(R.id.editText6);
        comm=findViewById(R.id.comment);
        attmpt=findViewById(R.id.attempted);
    }

    public void add_performance(View v)
    {
        dbStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                suname=sid.getText().toString();
                SPname = spname.getText().toString();
                Tname=tstname.getText().toString();
                cmmt=comm.getText().toString();
                String tot1=total.getText().toString();
                attpt=attmpt.getText().toString();
                String corr1=correct.getText().toString();
                String incorr1=incorrect.getText().toString();
                String tot2=totalmarks.getText().toString();
                Log.d("WORK",suname);
                Log.d("WORK",SPname);
                Log.d("WORK",Tname);
                Log.d("WORK",cmmt);
                Log.d("WORK",tot1);
                Log.d("WORK",corr1);
                Log.d("WORK",incorr1);
                Log.d("WORK", tot2);
                Log.d("WORK",attpt);
                if (validateTor.isEmpty(suname)) {
                    failFlag=true;
                    sid.setError("Field is empty!");
                }
                if (validateTor.isEmpty(cmmt)) {
                    failFlag=true;
                    spname.setError("Field is empty!");
                }
                if (validateTor.isEmpty(attpt)) {
                    failFlag=true;
                    spname.setError("Field is empty!");
                }
                if (validateTor.isEmpty(SPname)) {
                    failFlag=true;
                    spname.setError("Field is empty!");
                }
                if (validateTor.isEmpty(tot1)) {
                    failFlag=true;
                    total.setError("Field is empty!");
                }
                if (validateTor.isEmpty(corr1)) {
                    failFlag=true;
                    correct.setError("Field is empty!");
                }
                if (validateTor.isEmpty(attpt)) {
                    failFlag=true;
                    attmpt.setError("Field is empty!");
                }
                if (validateTor.isEmpty(cmmt)) {
                    failFlag=true;
                    comm.setError("Field is empty!");
                }
                if (validateTor.isEmpty(incorr1)) {
                    failFlag=true;
                    incorrect.setError("Field is empty!");
                }
                if (validateTor.isEmpty(tot2)) {
                    failFlag=true;
                    incorrect.setError("Field is empty!");
                }
                if(!failFlag) {
                    Log.d("WORK","FAIL");
                    Attempted= Integer.parseInt(attpt);
                    Total = Integer.parseInt(tot1);
                    Correct = Integer.parseInt(corr1);
                    Incorrect = Integer.parseInt(incorr1);
                    Totalmarks = Integer.parseInt(tot2);
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String name = dataSnapshot1.child("sid").getValue().toString();
                        snames.add(dataSnapshot1.child("sid").getValue().toString());
                        Log.d("TAG", name);
                    }
                    if (snames.contains(suname)) {

                        Performance performance = new Performance(SPname,class_selected, Correct, Total, Incorrect,Attempted,Tname,cmmt);
                        databasePerformance.child(suname).push().setValue(performance);
                        FBToast.successToast(getApplicationContext(), "Performance Added Successfully", Toast.LENGTH_LONG);
                        finish();

                    } else {
                        spname.setError("Student does not exist");
                        FBToast.warningToast(getApplicationContext(), "Pls Check student ID", Toast.LENGTH_LONG);
                    }
                }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);
            }
        });

        }
    }

