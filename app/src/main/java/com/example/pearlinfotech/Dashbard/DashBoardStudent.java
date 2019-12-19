package com.example.pearlinfotech.Dashbard;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.pearlinfotech.Admin.ProfileStudent;
import com.example.pearlinfotech.Attendance.AttendanceStudent;
import com.example.pearlinfotech.ExamDetail.ExamStudent;
import com.example.pearlinfotech.Fees.student_fees;
import com.example.pearlinfotech.HomeScreen.MainActivity;
import com.example.pearlinfotech.Performances.Performance_student;
import com.example.pearlinfotech.R;
import com.example.pearlinfotech.TimeTable.GetTT;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DashBoardStudent extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar mActionBarToolbar;
    CardView cv1,cv2,cv3,cv4,cv5,cv6;
    FirebaseDatabase db;
    String sname;
    String message;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_student);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");
        Toolbar toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle(message + "'s Dashboard  - " + date);
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
                basket.putString("sname",message);
                i.putExtras(basket);
                startActivity(i);
            }
        });
        cv2=findViewById(R.id.feesdashscard);
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
        cv3=findViewById(R.id.perfdashscard);
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i=new Intent(DashBoardStudent.this, Performance_student.class);
                Bundle basket= new Bundle();
                basket.putString("sname",message);
                i.putExtras(basket);
                startActivity(i);
            }
        });
        cv4=findViewById(R.id.examsdashscard);
        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DashBoardStudent.this, ExamStudent.class);
                Bundle basket= new Bundle();
                basket.putString("sname",sname);
                i.putExtras(basket);
                startActivity(i);
            }
        });
        cv5=findViewById(R.id.ttdashscard);
        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DashBoardStudent.this, GetTT.class);
                Bundle basket= new Bundle();
                basket.putString("sname",sname);
                i.putExtras(basket);
                startActivity(i);
            }
        });
        cv6=findViewById(R.id.profdashscard);
        cv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DashBoardStudent.this, ProfileStudent.class);
                Bundle basket= new Bundle();
                basket.putString("suid",message);
                i.putExtras(basket);
                startActivity(i);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public void logout(MenuItem item) {

        Intent logout=new Intent(DashBoardStudent.this, MainActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logout);

    }
}
