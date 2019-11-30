package com.example.pearlinfotech.Dashbard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pearlinfotech.Admin.FacultyList;
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
    }
}
