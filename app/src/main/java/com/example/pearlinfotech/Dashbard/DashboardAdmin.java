package com.example.pearlinfotech.Dashbard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pearlinfotech.Admin.FacultyList;
import com.example.pearlinfotech.Fees.fees_admin;
import com.example.pearlinfotech.R;

public class DashboardAdmin extends AppCompatActivity {
    CardView cv1,cv2,cv3,fee_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        cv1=findViewById(R.id.facultyacard);
        fee_admin=findViewById(R.id.feesdashacard);
        fee_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i=new Intent(DashboardAdmin.this, fees_admin.class);
                startActivity(i);
            }
        });
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DashboardAdmin.this, FacultyList.class);
                startActivity(i);
            }
        });
    }
}
