package com.example.pearlinfotech.Performance;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DatabaseReference;

public class Performance_student extends AppCompatActivity {

    TextView Sname, Topic, Total, Correct, Incorrect, Percentage, Tv3, Tv5, Tv7, Tv9, Tv11, Tv13;
    String sname, top, tot, crt, incrt, per, tv3, tv5, tv7, tv9, tv11, tv13;
    DatabaseReference databaseTeacher;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_student);

        Sname = findViewById(R.id.textView2);
        Tv3 = findViewById(R.id.textView3);
        Topic = findViewById(R.id.textView4);
        Tv5 = findViewById(R.id.textView5);
        Total = findViewById(R.id.textView6);
        Tv7 = findViewById(R.id.textView7);
        Correct = findViewById(R.id.textView8);
        Tv9 = findViewById(R.id.textView9);
        Incorrect = findViewById(R.id.textView10);
        Tv11 = findViewById(R.id.textView11);
        Percentage = findViewById(R.id.textView12);
        Tv13 = findViewById(R.id.textView13);
    }
}
