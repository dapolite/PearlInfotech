package com.example.pearlinfotech.Attendance;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tfb.fbtoast.FBToast;

import java.util.ArrayList;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;

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
        Toolbar toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle("View Attendance");
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
        String s="Shivangi";
        Bundle bundle1 = getIntent().getExtras();
        sid = bundle1.getString("sname");
        mDatabase = FirebaseDatabase.getInstance().getReference("StudentAttendance"+"/"+sid);

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
                 PieAngleAnimation animation1 = new PieAngleAnimation(prs);
                 animation1.setDuration(3000);
                 abs.setInnerText(String.valueOf(counta));
                 abs.setPercentageBackgroundColor(Color.MAGENTA);
                 PieAngleAnimation animation2 = new PieAngleAnimation(abs);
                 animation2.setDuration(3000);
                 tota.setInnerText(String.valueOf(tot));
                 tota.setPercentageBackgroundColor(Color.GREEN);
                 PieAngleAnimation animation3 = new PieAngleAnimation(tota);
                 animation3.setDuration(3000);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                 FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);
             }
         });


    }
}

