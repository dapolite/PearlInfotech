package com.example.pearlinfotech.ExamDetail;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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

public class ExamFaculty extends AppCompatActivity
{
    String ename,date,time;
    ArrayList<String> enames=new ArrayList<>();
    EditText e1,e2,e3;
    Toolbar mToolbar;
    DatabaseReference databaseExam;
    DatabaseReference dbStudent;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener exdate;
    TimePickerDialog.OnTimeSetListener extime;
    boolean failFlag=false;
    ValidateTor validateTor = new ValidateTor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_faculty);

        e1=findViewById(R.id.exmname);
        e2=findViewById(R.id.dateexam);
        e3=findViewById(R.id.timeexam);

        Toolbar toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle("Exam Details");
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
        databaseExam = FirebaseDatabase.getInstance().getReference("Exam");
        dbStudent = FirebaseDatabase.getInstance().getReference("Student");
        exdate  = new DatePickerDialog.OnDateSetListener(){    @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }};
        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ExamFaculty.this, exdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        extime  = new TimePickerDialog.OnTimeSetListener(){    @Override
        public void onTimeSet(TimePicker view, int hour, int min) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hour);
            myCalendar.set(Calendar.MINUTE, min);
            updateLabel2();
        }};
        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(ExamFaculty.this, extime, myCalendar
                        .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),true).show();
            }
        });
    }


    public void addExamDetail(View v) {
        ename = e1.getText().toString();
        date=e2.getText().toString();
        time=e3.getText().toString();
        dbStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (validateTor.isEmpty(ename)) {
                    failFlag=true;
                    e1.setError("Field is empty!");
                }
                else{
                    failFlag = false;
                }
                if (validateTor.isEmpty(date)) {
                    failFlag=true;
                    e2.setError("Field is empty!");
                }
                else{
                    failFlag = false;
                }
                if (validateTor.isEmpty(time)) {
                    failFlag=true;
                    e3.setError("Field is empty!");
                }
                else{
                    failFlag = false;
                }
                if(!failFlag)
                {
                    Exam exam = new Exam(ename,date,time);
                    databaseExam.child(ename).push().setValue(exam);
                    FBToast.successToast(getApplicationContext(), "Exam Details added successfully", Toast.LENGTH_LONG);
                    finish();
                }
                else {
                    FBToast.errorToast(getApplicationContext(), "Sorry! could not add exam details",Toast.LENGTH_LONG );
                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);
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

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        e2.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "hh:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        e3.setText(sdf.format(myCalendar.getTime()));
    }
}
