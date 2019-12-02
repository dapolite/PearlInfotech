package com.example.pearlinfotech.Dashbard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pearlinfotech.Admin.AddStudent;
import com.example.pearlinfotech.Admin.FacultyList;
import com.example.pearlinfotech.Fees.fees_admin;
import com.example.pearlinfotech.R;

public class DashboardAdmin extends AppCompatActivity {
    CardView cv1,cv2,cv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        cv1=findViewById(R.id.facultyacard);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DashboardAdmin.this, FacultyList.class);
                startActivity(i);
            }
        });
        cv2=findViewById(R.id.feesdashacard);
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DashboardAdmin.this, fees_admin.class);
                startActivity(i);
            }
        });
        cv3=findViewById(R.id.studdashacard);
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DashboardAdmin.this, AddStudent.class);
                startActivity(i);
            }
        });



    }
}
