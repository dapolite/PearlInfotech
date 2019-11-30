package com.example.pearlinfotech.Dashbard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.pearlinfotech.Attendance.AttendanceFaculty;
import com.example.pearlinfotech.R;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_faculty);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        mActionBarToolbar = findViewById(R.id.ftoolbar);
        Spinner spinner2 = findViewById(R.id.spinner2);


        //to get username from login page
        Bundle bundle1 = getIntent().getExtras();
        message = bundle1.getString("message");
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle(message+"'s Dashboard  - "+date);

        TextView txtView = findViewById(R.id.tvdashf);
        txtView.setText("Welcome : "+message);
        spinner2.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("CSE");
        categories.add("ME");
        categories.add("CE");
        categories.add("E");
        categories.add("D");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

        atten=findViewById(R.id.attendancefcard);
        atten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle basket= new Bundle();
                basket.putString("class_selected", item);
                basket.putString("tid", message);
                Intent intent = new Intent(DashboardFaculty.this, AttendanceFaculty.class);
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
}
