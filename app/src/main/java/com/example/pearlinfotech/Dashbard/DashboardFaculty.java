package com.example.pearlinfotech.Dashbard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.example.pearlinfotech.Admin.ProfileFaculty;
import com.example.pearlinfotech.Attendance.AttendanceFaculty;
import com.example.pearlinfotech.ExamDetail.ExamFaculty;
import com.example.pearlinfotech.HomeScreen.MainActivity;
import com.example.pearlinfotech.Message.MessageSend;
import com.example.pearlinfotech.Performances.Performance_faculty;
import com.example.pearlinfotech.R;
import com.example.pearlinfotech.TimeTable.UploadTT;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tfb.fbtoast.FBToast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardFaculty extends AppCompatActivity {
    private static long back_pressed;
    Toolbar mActionBarToolbar;
CardView atten,timetable,perf,notif,exam,prof;
String message,class_selected;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbTeacher;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle basket = new Bundle();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_faculty);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Bundle bundle1 = getIntent().getExtras();
        message = bundle1.getString("message");
        dbTeacher=ref.child("Faculty");
        dbTeacher.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                class_selected=dataSnapshot.child(message).child("classes").getValue().toString();
                Log.d("TAG",class_selected);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
        TextView txtView = findViewById(R.id.tvdashf);
        txtView.setText("Welcome : " + message);
        exam=findViewById(R.id.examsdashfcard);
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //basket.putString("class_selected", item);
                basket.putString("tid", message);
                basket.putString("class_selected", class_selected);
                Intent intent = new Intent(DashboardFaculty.this, ExamFaculty.class);
                intent.putExtras(basket);
                startActivity(intent);
            }
        });
        prof=findViewById(R.id.profdashfcard);
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //basket.putString("class_selected", item);
                basket.putString("tid", message);
                basket.putString("class_selected", class_selected);
                Intent intent = new Intent(DashboardFaculty.this, ProfileFaculty.class);
                intent.putExtras(basket);
                startActivity(intent);
            }
        });
        atten = findViewById(R.id.attendancefcard);
        atten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                basket.putString("class_selected", class_selected);
                basket.putString("tid", message);
                Intent intent = new Intent(DashboardFaculty.this, AttendanceFaculty.class);
                intent.putExtras(basket);
                startActivity(intent);
            }
        });
        perf = findViewById(R.id.perfdashfcard);
        perf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basket.putString("class_selected", class_selected);
                basket.putString("tid", message);

                Intent intent = new Intent(DashboardFaculty.this, Performance_faculty.class);
                intent.putExtras(basket);
                startActivity(intent);
            }
        });

        notif=findViewById(R.id.notifdashfcard);
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basket.putString("class_selected", class_selected);
                basket.putString("tid", message);

                Intent intent = new Intent(DashboardFaculty.this, MessageSend.class);
                intent.putExtras(basket);
                startActivity(intent);
            }
        });

        timetable=findViewById(R.id.ttdashfcard);
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //basket.putString("class", item);
                basket.putString("tid", message);

                Intent intent = new Intent(DashboardFaculty.this, UploadTT.class);
                intent.putExtras(basket);
                startActivity(intent);
            }
        });

        notif=findViewById(R.id.notifdashfcard);
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basket.putString("class_selected", class_selected);
                basket.putString("tid", message);

                Intent intent = new Intent(DashboardFaculty.this, MessageSend.class);
                intent.putExtras(basket);
                startActivity(intent);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public void logout(MenuItem item) {

        Intent logout=new Intent(DashboardFaculty.this, MainActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        SharedPreferences sharedpreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        startActivity(logout);

    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        } else {
            FBToast.infoToast(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT);
            back_pressed = System.currentTimeMillis();
        }
    }
}
