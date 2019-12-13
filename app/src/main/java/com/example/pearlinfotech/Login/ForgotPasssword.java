package com.example.pearlinfotech.Login;

import android.os.Build;
import android.os.Bundle;
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

public class ForgotPasssword extends AppCompatActivity {
EditText uid,pas;
String un,up;
DatabaseReference dbfac,dbstud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passsword);
        uid=findViewById(R.id.userid);
        pas=findViewById(R.id.pass);
        dbfac= FirebaseDatabase.getInstance().getReference("Faculty");
        dbstud= FirebaseDatabase.getInstance().getReference("Student");
        un=uid.getText().toString();
        up=pas.getText().toString();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void changepassword(View view) {
        dbfac.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(un).exists()){
                    dbfac.child(un).child("tpass").setValue(up);
                    Toast.makeText(ForgotPasssword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                }
                else{
                    uid.setError("Invalid userid");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbstud.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(un).exists()){
                    dbstud.child(un).child("spass").setValue(up);
                    Toast.makeText(ForgotPasssword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                }
                else{
                    uid.setError("Invalid userid");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    }

