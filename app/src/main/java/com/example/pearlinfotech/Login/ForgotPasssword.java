package com.example.pearlinfotech.Login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raywenderlich.android.validatetor.ValidateTor;
import com.tfb.fbtoast.FBToast;

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
        if (up.length() > 8 && validateTor.hasAtleastOneDigit(up)
                && validateTor.hasAtleastOneUppercaseCharacter(up)
                && validateTor.hasAtleastOneSpecialCharacter(up)) {
            failFlag = false;

        }
        else{
            failFlag=true;
            pas.setError("Password needs to be of minimum length of 8 characters and should have " +
                    "atleast 1 digit, 1 upppercase letter and 1 special character ");
            Log.d("TAG","spass");
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
            else{
                FBToast.warningToast(ForgotPasssword.this,"Must Be a Faculty",FBToast.LENGTH_SHORT);
            }
            if (item.equals("Student")){
                dbstud.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(un).exists()) {
                            Log.d("TAG", un);
                            Log.d("TAG", up);
                            dbstud.child(un).child("spass").setValue(up);
                            Toast.makeText(ForgotPasssword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            uid.setError("Invalid userid");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            else{
                FBToast.warningToast(ForgotPasssword.this,"Must Be a Student",FBToast.LENGTH_SHORT);
            }
        }

    }
    }

