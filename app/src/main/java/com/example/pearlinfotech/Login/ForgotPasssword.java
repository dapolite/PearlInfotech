package com.example.pearlinfotech.Login;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raywenderlich.android.validatetor.ValidateTor;

import java.util.ArrayList;
import java.util.List;

public class ForgotPasssword extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
EditText uid,pas;
String un,up;
    String item;
boolean failFlag = false;
ValidateTor validateTor=new ValidateTor();
DatabaseReference dbfac,dbstud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passsword);
        Spinner spinner = findViewById(R.id.spinner1);

        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("Faculty");
        categories.add("Student");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        uid=findViewById(R.id.userid);
        pas=findViewById(R.id.pass);
        dbfac= FirebaseDatabase.getInstance().getReference("Faculty");
        dbstud= FirebaseDatabase.getInstance().getReference("Student");

    }

    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void changepassword(View view) {
        un=uid.getText().toString();
        up=pas.getText().toString();
        if(validateTor.isEmpty(un)){
            failFlag=true;
            uid.setError("Field Cannot be Empty");
        }
        if(validateTor.isEmpty(up)){
            failFlag=true;
            pas.setError("Field Cannot be Empty");
        }
        if(!failFlag) {
            if (item.equals("Faculty")) {
                dbfac.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(un).exists()) {
                            dbfac.child(un).child("tpass").setValue(up);
                            Toast.makeText(ForgotPasssword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            uid.setError("Invalid userid");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            if (item.equals("Faculty")){
                dbstud.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(un).exists()) {
                            Log.d("TAG", un);
                            Log.d("TAG", up);
                            dbstud.child(un).child("spass").setValue(up);
                            Toast.makeText(ForgotPasssword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            uid.setError("Invalid userid");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        }

    }
    }

