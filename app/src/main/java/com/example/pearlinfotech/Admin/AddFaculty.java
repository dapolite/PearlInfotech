package com.example.pearlinfotech.Admin;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
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
import com.tfb.fbtoast.FBToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
public class AddFaculty extends AppCompatActivity {
    EditText Tname;
    DatePickerDialog.OnDateSetListener date;
    EditText Tid;
    EditText subject, tpassword,Tphno,Temail,Tdate;
    String tname, tid, sub, classname, tpass,tphno,temail,tdate;

    Calendar myCalendar = Calendar.getInstance();
    DatabaseReference databaseTeacher;

    Spinner classes;
    ValidateTor validateTor = new ValidateTor();
    boolean failFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        Toolbar toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle("Add Faculty");
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
        databaseTeacher = FirebaseDatabase.getInstance().getReference("Faculty");
        Temail=findViewById(R.id.emailaf);
        Tphno=findViewById(R.id.phnoaf);
        Tdate=findViewById(R.id.dateaf);
        Tname = findViewById(R.id.editText1);
        Tid = findViewById(R.id.editText3);
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
                DatePickerDialog dp=new DatePickerDialog(AddFaculty.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                dp.show();
            }
        });
    }

    public void addTeacher(View v) {
        temail=Temail.getText().toString();
        tphno=Tphno.getText().toString();
        tname = Tname.getText().toString();
        tid = Tid.getText().toString();
        classname = classes.getSelectedItem().toString();
        tpass = tpassword.getText().toString();
        tdate=Tdate.getText().toString();
        if (validateTor.isEmpty(temail)) {
            failFlag = true;
            Temail.setError("Field is empty!");
        }
        else{
            failFlag = false;
        }
        if (!validateTor.isEmail(temail)) {
            failFlag = true;
            Temail.setError("Not Valid Email!");
        }
        else{
            failFlag = false;
        }
        if (!PhoneNumberUtils.isGlobalPhoneNumber(tphno)) {
            failFlag = true;
            Tphno.setError("Not Valid Number!");
        }
        else{
            failFlag = false;
        }
        if (validateTor.isEmpty(tphno)) {
            failFlag = true;
            Tphno.setError("Field is empty!");
        }
        else{
            failFlag = false;
        }
        if (validateTor.isEmpty(tname)) {
            failFlag = true;
            Tname.setError("Field is empty!");
        }
        else{
            failFlag = false;
        }
        if (validateTor.isEmpty(tid)) {
            failFlag = true;
            Tid.setError("Field is empty!");
        }
        else{
            failFlag = false;
        }
        if (validateTor.isEmpty(tdate)) {
            failFlag = true;
            Tdate.setError("Field is empty!");
        }
        else{
            failFlag = false;
        }
        if (!validateTor.isAtleastLength(tpass, 8)
                && !validateTor.hasAtleastOneDigit(tpass)
                && !validateTor.hasAtleastOneUppercaseCharacter(tpass)
                && !validateTor.hasAtleastOneSpecialCharacter(tpass)) {
            failFlag = true;
            tpassword.setError("Password needs to be of minimum length of 8 characters and should have " +
                    "atleast 1 digit, 1 upppercase letter and 1 special character ");
        }
        else{
            failFlag = false;
        }
        if(!failFlag) {
            databaseTeacher.orderByChild("tid").equalTo(tid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(AddFaculty.this);
                        builder.setMessage("User Exists Do You Want to Update Details?")
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Faculty faculty = new Faculty(tname, tid, classname, tpass, temail, tphno, tdate);
                                        databaseTeacher.child(tid).setValue(faculty);
                                        FBToast.successToast(getApplicationContext(), "Faculty Updated successfully", Toast.LENGTH_LONG);
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
                        Faculty faculty = new Faculty(tname,tid,temail,classname,tpass,tdate,tphno);
                        databaseTeacher.child(tid).setValue(faculty);
                        FBToast.successToast(getApplicationContext(), "Faculty added successfully", Toast.LENGTH_LONG);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);

                }
            });

        }
        else{
            FBToast.warningToast(AddFaculty.this,"Make Sure All Feilds are Proper",FBToast.LENGTH_SHORT);
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
