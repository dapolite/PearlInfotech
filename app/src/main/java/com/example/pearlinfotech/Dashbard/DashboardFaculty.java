package com.example.pearlinfotech.Dashbard;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.pearlinfotech.Attendance.AttendanceFaculty;
import com.example.pearlinfotech.HomeScreen.MainActivity;
import com.example.pearlinfotech.Message.MessageSend;
import com.example.pearlinfotech.Performances.Performance_faculty;
import com.example.pearlinfotech.R;
import com.example.pearlinfotech.TimeTable.UploadTT;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardFaculty extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
Toolbar mActionBarToolbar;
CardView atten,timetable,perf,notif;
String message;

    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle basket = new Bundle();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_faculty);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        mActionBarToolbar = findViewById(R.id.ftoolbar);
        Spinner spinner2 = findViewById(R.id.spinner2);


        Bundle bundle1 = getIntent().getExtras();
        message = bundle1.getString("message");
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle(message + "'s Dashboard  - " + date);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView txtView = findViewById(R.id.tvdashf);
        txtView.setText("Welcome : " + message);
        spinner2.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Android");
        categories.add("Python");
        categories.add("C++");
        categories.add("Java");
        categories.add("HTML");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

        atten = findViewById(R.id.attendancefcard);
        atten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                basket.putString("class_selected", item);
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
                basket.putString("class", item);
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
                basket.putString("class", item);
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
                basket.putString("class", item);
                basket.putString("tid", message);

                Intent intent = new Intent(DashboardFaculty.this, UploadTT.class);
                intent.putExtras(basket);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public void logout(MenuItem item) {

        Intent logout=new Intent(DashboardFaculty.this, MainActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logout);

    }
}
