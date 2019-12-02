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
        mDatabase = FirebaseDatabase.getInstance().getReference("StudentAttendance");

        Bundle bundle1 = getIntent().getExtras();
        sname = bundle1.getString("sname");
        Log.d("TAG",sname);
       // Log.d("TAG",String.valueOf(count));
         mDatabase.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                      num2=snapshot.getKey();
                      num=snapshot.getValue().toString();
                     //if(num=="Present"){
                     //    count=counta++;
                     //}

                 }
                 //Log.d("TAG",String.valueOf(count));
                 Log.d("TAG",num + " " + num2);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
    }
}

