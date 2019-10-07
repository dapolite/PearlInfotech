package com.example.pearlinfotech.Dashbard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.pearlinfotech.Attendance.AttendanceFacultyExtra;
import com.example.pearlinfotech.R;

public class DashboardFaculty extends AppCompatActivity  {
Toolbar mActionBarToolbar;
CardView atten,timetable,perf,notif;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_faculty);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user=preferences.getString("uname",null);
        mActionBarToolbar = findViewById(R.id.toolbardashf);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle(user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        atten=findViewById(R.id.attendancefcard);
        atten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardFaculty.this,AttendanceFacultyExtra.class);
                startActivity(intent);
            }
        });

    }

}
