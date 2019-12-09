package com.example.pearlinfotech.Admin;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raywenderlich.android.validatetor.ValidateTor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;


public class AddStudent extends AppCompatActivity implements SlyCalendarDialog.Callback {
    EditText Sname, Sphno, Semail, Sdate;
    EditText Sid, spassword;
    String sname, sid, classname, spass, sphno, semail, sdate;
    Spinner classes;
    DatabaseReference databaseStudent;
    Toolbar mToolbar;
    ValidateTor validateTor = new ValidateTor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Toolbar toolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        databaseStudent = FirebaseDatabase.getInstance().getReference("Student");
        Semail = findViewById(R.id.emailas);
        Sphno = findViewById(R.id.phnoas);
        Sdate = findViewById(R.id.dateas);
        Sname = findViewById(R.id.editText1);
        Sid = findViewById(R.id.editText3);
        classes = findViewById(R.id.spinner3);
        spassword = findViewById(R.id.editText4);
        mToolbar = findViewById(R.id.ftoolbar);
        Sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlyCalendarDialog()
                        .setSingle(true)
                        .setFirstMonday(false)
                        .setCallback(AddStudent.this)
                        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
            }
        });
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add/Remove Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void addStudent(View v) {
        sdate = Sdate.getText().toString();
        semail = Semail.getText().toString();
        sphno = Sphno.getText().toString();
        sname = Sname.getText().toString();
        sid = Sid.getText().toString();
        classname = classes.getSelectedItem().toString();
        spass = spassword.getText().toString();

        boolean failFlag = false;
        if (validateTor.isEmpty(semail)) {
            failFlag = true;
            Semail.setError("Field is empty!");

        }
        if (!validateTor.isEmail(semail)) {
            failFlag = true;
            Semail.setError("Not Valid Email!");

        }
        if(!android.util.Patterns.PHONE.matcher(sphno).matches()) {
            failFlag = true;
            Sphno.setError("Invalid phoone number");

        }
        if (validateTor.isEmpty(sphno)) {
            failFlag = true;
            Sphno.setError("Field is empty!");

        }
        if (validateTor.isEmpty(sname)) {
            failFlag = true;
            Sname.setError("Field is empty!");

        }
        if (validateTor.isEmpty(sid)) {
            Sid.setError("Field is empty!");

        }
        if (validateTor.isEmpty(sdate)) {
            failFlag = true;
            Sdate.setError("Field is empty!");

        }
        if (!validateTor.isAtleastLength(spass, 8)
                && !validateTor.hasAtleastOneDigit(spass)
                && !validateTor.hasAtleastOneUppercaseCharacter(spass)
                && !validateTor.hasAtleastOneSpecialCharacter(spass)) {
            failFlag = true;
            spassword.setError("Password needs to be of minimum length of 8 characters and should have " +
                    "atleast 1 digit, 1 upppercase letter and 1 special character ");

        }
        if(failFlag == false){
            Student student = new Student(sname, sid, classname, spass, semail, sphno, sdate);
            databaseStudent.child(sid).setValue(student);
            Toast.makeText(getApplicationContext(), "student added successfully", Toast.LENGTH_LONG).show();
        }
    }

    public void removeStudent(View v) {
        if (!TextUtils.isEmpty(Sid.getText().toString())) {
            sid = Sid.getText().toString();
            databaseStudent.child(sid).setValue(null);
            Toast.makeText(getApplicationContext(), "teacher removed successfully", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "id cannot be empty", Toast.LENGTH_LONG).show();
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
                Sdate.setText(new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(firstDate.getTime()));
                sdate=new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(firstDate.getTime());

            } else {
                Sdate.setText(new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(firstDate.getTime()));
                sdate=new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(firstDate.getTime());
            }
        }
    }
}