package com.example.pearlinfotech.Dashbard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pearlinfotech.Attendance.AttendanceStudent;
import com.example.pearlinfotech.R;
import com.example.pearlinfotech.student_fees;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DashBoardStudent extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar mActionBarToolbar;
    CardView cv1,cv2,cv3,cv4,cv5;
    FirebaseDatabase db;
    String sname;
    String message;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_student);
        mActionBarToolbar = findViewById(R.id.ftoolbar);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");
        mActionBarToolbar.setTitle(message+"'s Dashboard"+"("+date+")");
//        TextView txtView = (TextView) findViewById(R.id.textView1);
        db=FirebaseDatabase.getInstance();
        ref=db.getReference("Student").child(message);
//        txtView.setText("Welcome :"+message);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sname=dataSnapshot.child("sname").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        cv1=findViewById(R.id.attendancescard);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DashBoardStudent.this, AttendanceStudent.class);
                Bundle basket= new Bundle();
                basket.putString("sname",sname);
                i.putExtras(basket);
                startActivity(i);
            }
        });
        cv2=findViewById(R.id.feesdashacard);
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i=new Intent(DashBoardStudent.this, student_fees.class);
                Bundle basket= new Bundle();
                basket.putString("sname",sname);
                i.putExtras(basket);
                startActivity(i);
            }
        });
    }
}
