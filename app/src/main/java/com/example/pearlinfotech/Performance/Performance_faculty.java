package com.example.pearlinfotech.Performance;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DatabaseReference;

public class Performance_faculty extends AppCompatActivity {

    EditText Sname, topic, total, correct, incorrect;
    Button btn;
    String sname, top, tot, crt, incrt;
    DatabaseReference databaseTeacher;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_faculty);

        Sname = findViewById(R.id.editText1);
        topic = findViewById(R.id.editText2);
        total = findViewById(R.id.editText3);
        correct = findViewById(R.id.editText4);
        incorrect = findViewById(R.id.editText5);

    }
}
