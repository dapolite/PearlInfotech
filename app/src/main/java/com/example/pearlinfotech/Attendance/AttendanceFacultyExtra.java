package com.example.pearlinfotech.Attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.R;

import java.util.Calendar;

public class AttendanceFacultyExtra extends AppCompatActivity {
    EditText crs;
    String date;
    String course;
    Calendar calendar;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_faculty_extra);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.YEAR, 2012);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date=i+"/"+i1+"/"+i2;
            }
        });
        Button button=findViewById(R.id.srccoursebtn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crs=findViewById(R.id.srccourse);
                course=crs.getText().toString();
                Intent intent = new Intent(AttendanceFacultyExtra.this, AttendanceFaculty.class);
                intent.putExtra("COURSE",course);
                intent.putExtra("DATE",date);
                startActivity(intent);
            }
        });
    }
}
