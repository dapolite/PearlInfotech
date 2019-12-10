package com.example.pearlinfotech.Admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;


public class AddFaculty extends AppCompatActivity implements SlyCalendarDialog.Callback {
    EditText Tname;
    EditText Tid;
    EditText subject, tpassword,Tphno,Temail,Tdate;
    String tname, tid, sub, classname, tpass,tphno,temail,tdate;
    Spinner classes;
    Button addButton;
    DatabaseReference databaseTeacher;
    Toolbar mToolbar;
    boolean failFlag = false;
    ValidateTor validateTor = new ValidateTor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);
        Toolbar toolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add Faculty");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        databaseTeacher = FirebaseDatabase.getInstance().getReference("Faculty");
        Temail=findViewById(R.id.emailaf);
        Tphno=findViewById(R.id.phnoaf);
        Tdate=findViewById(R.id.dateaf);
        Tname = findViewById(R.id.editText1);
        Tid = findViewById(R.id.editText3);
        //subject = findViewById(R.id.editText4);
        classes = findViewById(R.id.spinner3);
        tpassword = findViewById(R.id.editText5);
        mToolbar = findViewById(R.id.ftoolbar);
        Tdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlyCalendarDialog()
                        .setSingle(true)
                        .setFirstMonday(false)
                        .setCallback(AddFaculty.this)
                        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
            }
        });
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add/Remove Teacher");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void addTeacher(View v) {
        tdate=Tdate.getText().toString();
        temail=Temail.getText().toString();
        tphno=Tphno.getText().toString();
        tname = Tname.getText().toString();
        tid = Tid.getText().toString();
        //sub = subject.getText().toString();
        classname = classes.getSelectedItem().toString();
        tpass = tpassword.getText().toString();
        databaseTeacher.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fsid=dataSnapshot.child("tid").toString();
                String fsemail=dataSnapshot.child("temail").toString();
                String fspass=dataSnapshot.child("tpass").toString();
                String fsphno=dataSnapshot.child("tphno").toString();
                if(fsid.equals(tid)){
                    failFlag=true;
                    Tid.setError("Username Taken");
                }
                if(fsemail.equals(temail)){
                    failFlag=true;
                    Temail.setError("Email Aleady Exists");
                }
                if(fspass.equals(tpass)){
                    failFlag=true;
                    tpassword.setError("Password Taken");
                }
                if(fsphno.equals(tphno)){
                    failFlag=true;
                    Tid.setError("Phone Number Aready Exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (!validateTor.isEmpty(temail)) {
            failFlag = true;
            Temail.setError("Field is empty!");
        }
        if (!validateTor.isEmail(temail)) {
            failFlag = true;
            Temail.setError("Not Valid Email!");
        }
        if (!validateTor.isPhoneNumber(tphno)) {
            failFlag = true;
            Tphno.setError("Not Valid Number!");
        }
        if (validateTor.isEmpty(tphno)) {
            failFlag = true;
            Tphno.setError("Field is empty!");
        }
        if (validateTor.isEmpty(tname)) {
            failFlag = true;
            Tname.setError("Field is empty!");
        }
        if (validateTor.isEmpty(tid)) {
            failFlag = true;
            Tid.setError("Field is empty!");
        }
        if (validateTor.isAtleastLength(tpass, 8)
                && validateTor.hasAtleastOneDigit(tpass)
                && validateTor.hasAtleastOneUppercaseCharacter(tpass)
                && validateTor.hasAtleastOneSpecialCharacter(tpass)) {
            failFlag = true;
            tpassword.setError("Password needs to be of minimum length of 8 characters and should have " +
                    "atleast 1 digit, 1 upppercase letter and 1 special character ");

        }
        if(failFlag == false){
            Faculty teacher = new Faculty(tname, tid, classname, tpass,temail,tphno,tdate);
            databaseTeacher.child(tid).setValue(teacher);
            Toast.makeText(getApplicationContext(), "Teacher added successfully", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
        if (firstDate != null) {
            if (secondDate == null) {
                Tdate.setText(new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(DateFormat.getDateInstance()));
                tdate=new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(firstDate.getTime());

            } else {
                Tdate.setText(new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(firstDate.getTime()));
                tdate=new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(firstDate.getTime());
            }
        }
    }
}
