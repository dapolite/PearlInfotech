package com.example.pearlinfotech.Admin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import java.util.Calendar;
import java.util.Locale;


public class AddFaculty extends AppCompatActivity {
    EditText Tname;
    DatePickerDialog.OnDateSetListener date;
    EditText Tid;
    EditText subject, tpassword,Tphno,Temail,Tdate;
    String tname, tid, sub, classname, tpass,tphno,temail,tdate;
    Spinner classes;
    Button addButton;
    Calendar myCalendar = Calendar.getInstance();
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
        date  = new DatePickerDialog.OnDateSetListener(){    @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }};
        Tdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddFaculty.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
        Log.d("TAG",tname);
        Log.d("TAG",tid);
        Log.d("TAG",classname);
        Log.d("TAG",tpass);
        Log.d("TAG",temail);
        Log.d("TAG",tphno);
        Log.d("TAG",tdate);
        databaseTeacher.orderByChild("tid").equalTo(tid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Tid.setError("User Exists");
                    failFlag=true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (validateTor.isEmpty(temail)) {
            failFlag = true;
            Temail.setError("Field is empty!");
        }
        if (!validateTor.isEmail(temail)) {
            failFlag = true;
            Temail.setError("Not Valid Email!");
        }
        if (!android.util.Patterns.PHONE.matcher(tphno).matches()) {
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
        if (!validateTor.isAtleastLength(tpass, 8)
                && !validateTor.hasAtleastOneDigit(tpass)
                && !validateTor.hasAtleastOneUppercaseCharacter(tpass)
                && !validateTor.hasAtleastOneSpecialCharacter(tpass)) {
            failFlag = true;
            tpassword.setError("Password needs to be of minimum length of 8 characters and should have " +
                    "atleast 1 digit, 1 upppercase letter and 1 special character ");

        }
        databaseTeacher.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fsid=dataSnapshot.child("tid").toString();
                String fsemail=dataSnapshot.child("temail").toString();
                String fspass=dataSnapshot.child("tpass").toString();
                String fsphno=dataSnapshot.child("tphno").toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(failFlag == false) {
            Faculty faculty = new Faculty(tname, tid, classname, tpass, temail, tdate, tphno);
            databaseTeacher.child(tid).setValue(faculty);
            Toast.makeText(getApplicationContext(), "Teacher added successfully", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void updateTeacher(View v) {
        tdate=Tdate.getText().toString();
        temail=Temail.getText().toString();
        tphno=Tphno.getText().toString();
        tname = Tname.getText().toString();
        tid = Tid.getText().toString();
        //sub = subject.getText().toString();
        classname = classes.getSelectedItem().toString();
        tpass = tpassword.getText().toString();
        Log.d("TAG",tname);
        Log.d("TAG",tid);
        Log.d("TAG",classname);
        Log.d("TAG",tpass);
        Log.d("TAG",temail);
        Log.d("TAG",tphno);
        Log.d("TAG",tdate);
        databaseTeacher.orderByChild("tid").equalTo(tid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    failFlag=true;
                    Tid.setError("User  Does Not Exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseTeacher.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fsid=dataSnapshot.child("tid").toString();
                String fsemail=dataSnapshot.child("temail").toString();
                String fspass=dataSnapshot.child("tpass").toString();
                String fsphno=dataSnapshot.child("tphno").toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (validateTor.isEmpty(temail)) {
            failFlag = true;
            Temail.setError("Field is empty!");
        }
        if (!validateTor.isEmail(temail)) {
            failFlag = true;
            Temail.setError("Not Valid Email!");
        }
        if (!android.util.Patterns.PHONE.matcher(tphno).matches()) {
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
        if (!validateTor.isAtleastLength(tpass, 8)
                && !validateTor.hasAtleastOneDigit(tpass)
                && !validateTor.hasAtleastOneUppercaseCharacter(tpass)
                && !validateTor.hasAtleastOneSpecialCharacter(tpass)) {
            failFlag = true;
            tpassword.setError("Password needs to be of minimum length of 8 characters and should have " +
                    "atleast 1 digit, 1 upppercase letter and 1 special character ");

        }
        if(failFlag == false) {
            Faculty faculty = new Faculty(tname, tid, classname, tpass, temail, tdate, tphno);
            databaseTeacher.child(tid).setValue(faculty);
            Toast.makeText(getApplicationContext(), "Teacher updated successfully", Toast.LENGTH_LONG).show();
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
    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Tdate.setText(sdf.format(myCalendar.getTime()));
    }
}
