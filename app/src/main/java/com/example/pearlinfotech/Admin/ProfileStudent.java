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

public class ProfileStudent extends AppCompatActivity {
    TextView stuname,sid,classes,spass,sphno,semail,sdate;
    Button button;
    String tid;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);
        Bundle bundle1 = getIntent().getExtras();
        tid = bundle1.getString("suid");
        stuname=findViewById(R.id.suname);
        sid=findViewById(R.id.suid);
        classes=findViewById(R.id.suclasse);
        spass=findViewById(R.id.supass);
        sphno=findViewById(R.id.suphno);
        semail=findViewById(R.id.suemail);
        sdate=findViewById(R.id.sudate);
        mRef = FirebaseDatabase.getInstance().getReference("Student/"+tid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String lol=ds.getValue().toString();
                    Log.d("TAG",lol);
                    String sname=dataSnapshot.child("sname").getValue().toString();
                    stuname.setText(sname);
                    String sids=dataSnapshot.child("sid").getValue().toString();
                    sid.setText(sids);
                    String sclass=dataSnapshot.child("classes").getValue().toString();
                    classes.setText(sclass);
                    String s=dataSnapshot.child("spass").getValue().toString();
                    spass.setText(s);
                    String ph=dataSnapshot.child("sphno").getValue().toString();
                    sphno.setText(ph);
                    String email=dataSnapshot.child("semail").getValue().toString();
                    semail.setText(email);
                    String date=dataSnapshot.child("sdate").getValue().toString();
                    sdate.setText(date);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
