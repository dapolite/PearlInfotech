package com.example.pearlinfotech.Fees;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

import java.util.ArrayList;

public class fees_admin extends AppCompatActivity
{
    String sname,course,type1,type2,paid1,total1,sid;
    int total,paid;
    ArrayList<String> snames=new ArrayList<>();
    EditText e1,e2,e3,e5;
    Spinner e4;
    Spinner spin1,spin2;
    Toolbar mToolbar;
    DatabaseReference databaseFees;
    DatabaseReference dbStudent;
    boolean failFlag=false;
    ValidateTor validateTor = new ValidateTor();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_admin);

        spin1=findViewById(R.id.feeadmininstallment);
        spin2=findViewById(R.id.feeadminmode);
        e2=findViewById(R.id.editTextsid);
        e1=findViewById(R.id.editText1);
        e3=findViewById(R.id.editText3);
        e4=findViewById(R.id.editText4);
        e5=findViewById(R.id.editText5);

        databaseFees = FirebaseDatabase.getInstance().getReference("Fees");
        dbStudent = FirebaseDatabase.getInstance().getReference("Student");
        Toolbar toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle("Fee Details");
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

    }


        public void addFees(View v) {
            Log.d("Working?","Working");
            sname = e1.getText().toString();
            total1=e3.getText().toString();
            sid=e2.getText().toString();
            course = e4.getSelectedItem().toString();
            paid1=e5.getText().toString();

            type1 = spin1.getSelectedItem().toString();
            type2 = spin2.getSelectedItem().toString();
            Log.d("Working?",sname);
            Log.d("Working?",sid);
            Log.d("Working?",total1);
            Log.d("Working?",course);
            Log.d("Working?",type2);
            Log.d("Working?",type1);
            Log.d("Working?",paid1);
            if (validateTor.isEmpty(sid)) {
                failFlag=true;
                e2.setError("Field is empty!");
            }
            if (validateTor.isEmpty(sname)) {
                failFlag=true;
                e1.setError("Field is empty!");
            }
            if (validateTor.isEmpty(total1)) {
                failFlag=true;
                e3.setError("Field is empty!");
            }

            if (validateTor.isEmpty(paid1)) {
                failFlag=true;
                e5.setError("Field is empty!");
            }
            if(!failFlag) {
                Log.d("Working","Fail");
                dbStudent.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        total = Integer.parseInt(total1);
                        paid = Integer.parseInt(paid1);
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            String id=dataSnapshot1.child("sid").getValue().toString();
                            Log.d("WORK",id);
                            snames.add(dataSnapshot1.child("sid").getValue().toString());
                        }
                        if (snames.contains(sname)) {
                            Log.d("Working","Fail2");
                            Fee fee = new Fee(sid, total, paid, course, type1, type2);
                            databaseFees.child(sname).setValue(fee);
                            FBToast.successToast(getApplicationContext(), "Fees added successfully", Toast.LENGTH_LONG);

                        } else {
                            e2.setError("Sorry Student does not Exist");
                            FBToast.warningToast(getApplicationContext(), "Pls Check student ID", Toast.LENGTH_LONG);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);
                    }
                });
            }
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
