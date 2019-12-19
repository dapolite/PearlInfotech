package com.example.pearlinfotech.Login;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.tfb.fbtoast.FBToast;

public class ForgotPasssword extends AppCompatActivity {
EditText uid,pas;
String un,up;
boolean failFlag = false;
ValidateTor validateTor=new ValidateTor();
DatabaseReference dbfac,dbstud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passsword);
        uid=findViewById(R.id.userid);
        pas=findViewById(R.id.pass);
        dbfac= FirebaseDatabase.getInstance().getReference("Faculty");
        dbstud= FirebaseDatabase.getInstance().getReference("Student");

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
            dbfac.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(un).exists()) {
                        dbfac.child(un).child("tpass").setValue(up);
                        FBToast.successToast(ForgotPasssword.this, "Password Changed", Toast.LENGTH_SHORT);
                    } else {
                        uid.setError("Invalid userid");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);
                }
            });

            dbstud.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(un).exists()) {
                        Log.d("TAG", un);
                        Log.d("TAG", up);
                        dbstud.child(un).child("spass").setValue(up);
                        FBToast.successToast(ForgotPasssword.this, "Password Changed", Toast.LENGTH_SHORT);
                    } else {
                        uid.setError("Invalid userid");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);
                }
            });
        }
    }
    }

