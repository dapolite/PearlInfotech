package com.example.pearlinfotech.Attendance;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AttendanceStudent extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextView mTvEmpty;
    String sname;
    String num,num2;
    ArrayList<String> data=new ArrayList<>();
    int count;
    private FirebaseDatabase mFirebaseInstance;
    int counta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_student);
        String s="Shivangi";
        Bundle bundle1 = getIntent().getExtras();
        sname = bundle1.getString("sname");
        mDatabase = FirebaseDatabase.getInstance().getReference("StudentAttendance"+"/"+s);


        Log.d("TAG",sname);

       // Log.d("TAG",String.valueOf(count));
         mDatabase.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                      num=snapshot.getValue().toString();
                      if(num.equals("Present")){
                          counta=counta+1;
                      }
                     //if(num=="Present"){
                     //    count=counta++;
                     //}

                 }
                 //Log.d("TAG",String.valueOf(count));
                 Log.d("TAG",num + " " + counta);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
    }
}

