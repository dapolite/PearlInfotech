package com.example.pearlinfotech.Performances;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Performance_faculty extends AppCompatActivity {

    EditText suname,suid, totalq, correct, incorrect,sid,totalmarks;
    ArrayList<String> sids=new ArrayList<>();
    EditText tstname,attmpt,comm,pdate;
    ValidateTor validateTor = new ValidateTor();
    String class_selected;
    Button btn;
    DatePickerDialog.OnDateSetListener date1;
    String Suname,Suid,Tname,attpt,cmmt,adate;
    String topic;
    int Total,Correct,Incorrect,Attempted,Totalmarks;
    DatabaseReference databasePerformance;
    DatabaseReference dbStudent;
    ArrayList<String> snames=new ArrayList<>();
    ArrayList<String> subs=new ArrayList<>();
    boolean failFlag=false;
    String dates;
    Calendar myCalendar= Calendar.getInstance();
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_faculty);
        Bundle bundle1 = getIntent().getExtras();
        class_selected=bundle1.getString("class_selected");
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
        suname = findViewById(R.id.editTextsname);
        tstname = findViewById(R.id.tstname);
        totalq = findViewById(R.id.editTextTotQues);
        correct = findViewById(R.id.editTextCorrect);
        incorrect = findViewById(R.id.editTextIncorrect);
        totalmarks = findViewById(R.id.editTextTotalMarks);
        comm=findViewById(R.id.comment);
        attmpt=findViewById(R.id.attempted);
        pdate=findViewById(R.id.pdate);
        date1  = new DatePickerDialog.OnDateSetListener(){    @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }};
        pdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Performance_faculty.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void add_performance(View v)
    {
        dbStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Suid=sid.getText().toString();
                Suname = suname.getText().toString();
                Tname=tstname.getText().toString();
                cmmt=comm.getText().toString();
                String tot1=totalq.getText().toString();
                attpt=attmpt.getText().toString();
                String corr1=correct.getText().toString();
                String incorr1=incorrect.getText().toString();
                String tot2=totalmarks.getText().toString();
                dates=pdate.getText().toString();
if(validateTor.isEmpty(dates)){
    failFlag=true;
    sid.setError("Field is empty!");
}
                if (validateTor.isEmpty(Suid)) {
                    failFlag=true;
                    sid.setError("Field is empty!");
                }
                if (validateTor.isEmpty(cmmt)) {
                    failFlag=true;
                    comm.setError("Field is empty!");
                }
                if (validateTor.isEmpty(attpt)) {
                    failFlag=true;
                    attmpt.setError("Field is empty!");
                }
                if (validateTor.isEmpty(Suname)) {
                    failFlag=true;
                    suname.setError("Field is empty!");
                }
                if (validateTor.isEmpty(tot1)) {
                    failFlag=true;
                    totalq.setError("Field is empty!");
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
                    totalmarks.setError("Field is empty!");
                }
                if(!failFlag) {
                    Log.d("WORK","FAIL");
                    Attempted= Integer.parseInt(attpt);
                    Total = Integer.parseInt(tot1);
                    Correct = Integer.parseInt(corr1);
                    Incorrect = Integer.parseInt(incorr1);
                    Totalmarks = Integer.parseInt(tot2);
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String id = dataSnapshot1.child("sid").getValue().toString();
                        String sn=dataSnapshot1.child("sname").getValue().toString();
                        sids.add(id);
                        snames.add(sn);

                    }
                    if (sids.contains(Suid)) {
                        if(snames.contains(Suname)){
                        Performance performance = new Performance(Suid,Suname,Tname, Total,Attempted,Correct, Incorrect,Totalmarks,cmmt,dates,class_selected);
                        databasePerformance.child(Suid).push().setValue(performance);
                        FBToast.successToast(getApplicationContext(), "Performance Added Successfully", Toast.LENGTH_LONG);
                        finish();
                        }
                        else{
                            suname.setError("Wrong Student Name");
                        }


                    } else {
                        sid.setError("Student does not exist");
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

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        pdate.setText(sdf.format(myCalendar.getTime()));
    }
    }

