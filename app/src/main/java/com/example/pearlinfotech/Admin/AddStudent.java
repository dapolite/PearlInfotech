package com.example.pearlinfotech.Admin;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class AddStudent extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = Calendar.getInstance();
    EditText Sname, Sphno, Semail, Sdate;
    EditText Sid, spassword;
    ArrayList<String> fsid=new ArrayList<>();
    ArrayList<String> fspass=new ArrayList<>();
    ArrayList<String> fsemail=new ArrayList<>();
    ArrayList<String> fsphno=new ArrayList<>();
    String sname, sid, classname, spass, sphno, semail, sdate;
    Spinner classes;
    DatabaseReference databaseStudent;
    Toolbar mToolbar;
    boolean  failFlag = false;
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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add/Remove Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        date = new DatePickerDialog.OnDateSetListener(){    @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }};
        Sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddStudent.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void addStudent(View v) {
        sdate = Sdate.getText().toString();
        semail = Semail.getText().toString();
        sphno = Sphno.getText().toString();
        sname = Sname.getText().toString();
        sid = Sid.getText().toString();
        classname = classes.getSelectedItem().toString();
        spass = spassword.getText().toString();
        Log.d("TAG",sdate);




        databaseStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    fsid.add(ds.child("sid").toString());
                    fsemail.add(ds.child("semail").toString());
                    fspass.add( ds.child("spass").toString());
                    fsphno.add(ds.child("sphno").toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(fsid.contains(sid)){
            failFlag=true;
            Sid.setError("Username Taken");
        }
        if(fsemail.contains(semail)){
            failFlag=true;
            Semail.setError("Email Aleady Exists");
        }
        if(fspass.contains(spass)){
            failFlag=true;
            spassword.setError("Password Taken");
        }
        if(fsphno.contains(sphno)){
            failFlag=true;
            Sid.setError("Phone Number Aready Exists");
        }
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
        if (!validateTor.isAtleastLength(spass, 8)
                && !validateTor.hasAtleastOneDigit(spass)
                && !validateTor.hasAtleastOneUppercaseCharacter(spass)
                && !validateTor.hasAtleastOneSpecialCharacter(spass)) {
            failFlag = true;
            spassword.setError("Password needs to be of minimum length of 8 characters and should have " +
                    "atleast 1 digit, 1 upppercase letter and 1 special character ");

        }
        databaseStudent.orderByChild("sid").equalTo(sid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Sid.setError("Username Taken");
                    failFlag=true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(failFlag == false){
            Student student = new Student(sname, sid, classname, spass, semail, sphno, sdate);
            databaseStudent.child(sid).setValue(student);
            Toast.makeText(getApplicationContext(), "student added successfully", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Cant Add User", Toast.LENGTH_SHORT).show();
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

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Sdate.setText(sdf.format(myCalendar.getTime()));
    }

}