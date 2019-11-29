package com.example.pearlinfotech.Dashbard;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.R;

public class DashBoardStudent extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar mActionBarToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_student);
        SharedPreferences sharedpreferences = getSharedPreferences("user",MODE_PRIVATE);
        mActionBarToolbar = findViewById(R.id.dshstudentbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
}
