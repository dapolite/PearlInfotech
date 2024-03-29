package com.example.pearlinfotech.Admin;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.tfb.fbtoast.FBToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddStudent extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = Calendar.getInstance();
    EditText Sname, Sphno, Semail, Sdate;
    EditText Sid, spassword;
    boolean ff=false;
    String sname, sid, classname, spass, sphno, semail, sdate;
    Spinner classes;
    DatabaseReference databaseStudent,databaseCourse;
    Toolbar mToolbar;
    boolean failFlag = false;
    ValidateTor validateTor = new ValidateTor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Toolbar toolbar1 = findViewById(R.id.ftoolbar);
        toolbar1.setTitle("Add Students");
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
        databaseStudent = FirebaseDatabase.getInstance().getReference("Student");
        databaseCourse=FirebaseDatabase.getInstance().getReference("Course");
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
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        Sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp=new DatePickerDialog(AddStudent.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                dp.show();
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
        if (validateTor.isEmpty(semail)) {
            Semail.setError("Field is empty!");
            failFlag = true;
            Log.d("TAG","semail");
        }
        else{
            failFlag = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            Semail.setError("Not Valid Email!");
            Log.d("TAG","semail");
            failFlag = true;
        }
        else{
            failFlag = false;
        }
        if (!Patterns.PHONE.matcher(sphno).matches() && sphno.length()<10) {
            Sphno.setError("Invalid phone number");
            Log.d("TAG","sphno");
            failFlag = true;
        }
        else{
            failFlag = false;
        }
        if (validateTor.isEmpty(sphno) ) {
            Sphno.setError("Field is empty!");
            failFlag = true;
        }
        else{
            failFlag = false;
        }
        if (validateTor.isEmpty(sname)) {
            Sname.setError("Field is empty!");
            Log.d("TAG","sname");
            failFlag = true;
        }
        else{
            failFlag = false;
        }
        if (validateTor.isEmpty(sid)) {
            Sid.setError("Field is empty!");
            Log.d("TAG","sid");
            failFlag = true;
        }
        else{
            failFlag = false;
        }
        if(validateTor.isEmpty(sdate)){
            Sdate.setError("Field Is Empty");
            Log.d("TAG","sdate");
            failFlag = true;
        }
        else{
            failFlag = false;
        }
        if (spass.length() > 8 && validateTor.hasAtleastOneDigit(spass)
                && validateTor.hasAtleastOneUppercaseCharacter(spass)
                && validateTor.hasAtleastOneSpecialCharacter(spass)) {
            failFlag = false;

        }
        else{
            failFlag=true;
            spassword.setError("Password needs to be of minimum length of 8 characters and should have " +
                    "atleast 1 digit, 1 upppercase letter and 1 special character ");
            Log.d("TAG","spass");
        }
        if (failFlag==false) {
            Log.d("TAG","FINE");
            databaseStudent.orderByChild("sid").equalTo(sid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(AddStudent.this);
                        builder.setMessage("User Exists Do You Want to Update Details?")
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Student student = new Student(sname, sid, classname, spass, semail, sphno, sdate);
                                        databaseStudent.child(sid).setValue(student);

                                        FBToast.successToast(getApplicationContext(), "student Updated successfully", Toast.LENGTH_LONG);
                                        finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setTitle("Woops!");
                        alert.show();
                    }
                    else{
                        Student student = new Student(sname, sid, classname, spass, semail, sphno, sdate);
                        databaseStudent.child(sid).setValue(student);
                        databaseCourse.child(sid).push().setValue(classname);
                        FBToast.successToast(getApplicationContext(), "student added successfully", Toast.LENGTH_LONG);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);

                }
            });

        }
        else {
            FBToast.errorToast(this, "Cant Add User", Toast.LENGTH_SHORT);
        }
    }

    public void addCourse(View v) {
        if (!TextUtils.isEmpty(Sid.getText().toString())) {
            sid = Sid.getText().toString();

            databaseStudent.orderByChild("sid").equalTo(sid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddStudent.this);
                        View mview = getLayoutInflater().inflate(R.layout.dialog_spinner_addcourse, null);
                        mBuilder.setTitle("Add Course");
                        Spinner mspinner = mview.findViewById(R.id.spinner4);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddStudent.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.courselist));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mspinner.setAdapter(adapter);
                        String classes = mspinner.getSelectedItem().toString();
                        mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String classe = mspinner.getSelectedItem().toString();
                                databaseCourse.child(sid).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                                            String c=ds.getValue().toString();
                                            if(c.equals(classe)){
                                                ff=true;
                                                FBToast.errorToast(AddStudent.this,"Course Taken By User",FBToast.LENGTH_SHORT);
                                            }
                                            else{
                                            }
                                        }
                                        if(!ff) {
                                            databaseCourse.child(sid).push().setValue(classe);
                                            FBToast.successToast(AddStudent.this, "Course Added", FBToast.LENGTH_SHORT);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });
                        mBuilder.setView(mview);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();
                    }
                }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);

                    }
                });
            }
            else {
            FBToast.errorToast(getApplicationContext(), "id cannot be empty", Toast.LENGTH_LONG);
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