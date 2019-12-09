package com.example.pearlinfotech.Attendance;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

public class AttendanceStudent extends AppCompatActivity {
    private DatabaseReference mDatabase;
    String sname;
    String num,num2;
    ArrayList<String> data=new ArrayList<>();
    TextView abs,prs,tota;
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
        sname = bundle1.getString("sname");
        mDatabase = FirebaseDatabase.getInstance().getReference("StudentAttendance"+"/"+sname);


        Log.d("TAG",sname);

       // Log.d("TAG",String.valueOf(count));
         mDatabase.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                      num=snapshot.getValue().toString();
                      if(num.equals("Present")){
                          countp=countp+1;
                      }
                      if(num=="Absent"){
                         counta=counta++;
                     }

                 }
                 tot=counta+countp;
                 //Log.d("TAG",String.valueOf(count));
                 Log.d("TAG",num + " " + counta);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
         prs.setText(String.valueOf(countp));
         abs.setText(String.valueOf(counta));
         tota.setText(String.valueOf(tot));

    }
}

