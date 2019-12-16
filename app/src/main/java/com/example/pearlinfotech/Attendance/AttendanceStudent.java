package com.example.pearlinfotech.Attendance;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import az.plainpie.PieView;

public class AttendanceStudent extends AppCompatActivity {
    private DatabaseReference mDatabase;
    String sid;
    String num,num2;
    ArrayList<String> data=new ArrayList<>();
    PieView abs,prs,tota;
    int countp,tot;
    private FirebaseDatabase mFirebaseInstance;
    int counta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_student);
        prs=findViewById(R.id.pres);
        abs=findViewById(R.id.abs);
        tota=findViewById(R.id.tot);
        Toolbar toolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String s="Shivangi";
        Bundle bundle1 = getIntent().getExtras();
        sid = bundle1.getString("sname");
        mDatabase = FirebaseDatabase.getInstance().getReference("StudentAttendance"+"/"+sid);


       // Log.d("TAG",String.valueOf(count));
         mDatabase.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                      num=snapshot.getValue().toString();
                      Log.d("TAG",num);
                      if(num.equals("Present")){
                          countp=countp+1;
                      }
                      if(num.equals("Absent")){
                         counta=counta+1;
                     }

                 }
                 tot=counta+countp;
                 //Log.d("TAG",String.valueOf(count));
                 Log.d("TAG",num + " " + countp);
                 prs.setInnerText(String.valueOf(countp));
                 abs.setInnerText(String.valueOf(counta));
                 tota.setInnerText(String.valueOf(tot));
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });


    }
}

