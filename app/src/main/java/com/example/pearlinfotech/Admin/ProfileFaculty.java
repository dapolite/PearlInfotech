package com.example.pearlinfotech.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFaculty extends AppCompatActivity {
    TextView stuname,sid,classes,spass,sphno,semail,sdate;
    Button button;
    String tid;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_faculty);
        Bundle bundle1 = getIntent().getExtras();
        tid = bundle1.getString("tid");
        stuname=findViewById(R.id.studname);
        sid=findViewById(R.id.uname);
        classes=findViewById(R.id.classe);
        spass=findViewById(R.id.upass);
        sphno=findViewById(R.id.phno);
        semail=findViewById(R.id.email);
        sdate=findViewById(R.id.date);
        mRef = FirebaseDatabase.getInstance().getReference("Faculty/"+tid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String lol=ds.getValue().toString();
                    Log.d("TAG",lol);
                    String sname=dataSnapshot.child("tname").getValue().toString();
                    stuname.setText(sname);
                    String sids=dataSnapshot.child("tid").getValue().toString();
                    sid.setText(sids);
                    String sclass=dataSnapshot.child("classes").getValue().toString();
                    classes.setText(sclass);
                    String s=dataSnapshot.child("tpass").getValue().toString();
                    spass.setText(s);
                    String ph=dataSnapshot.child("tphno").getValue().toString();
                    sphno.setText(ph);
                    String email=dataSnapshot.child("temail").getValue().toString();
                    semail.setText(email);
                    String date=dataSnapshot.child("tdate").getValue().toString();
                    sdate.setText(date);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
