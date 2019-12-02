package com.example.pearlinfotech.Performance;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Performance_faculty extends AppCompatActivity {

    EditText sname, topic, total, correct, incorrect;


    Button btn;
    String Sname;
    String Topic;
    int Total,Correct,Incorrect;
    DatabaseReference databasePerformance;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_faculty);

        databasePerformance = FirebaseDatabase.getInstance().getReference("Performance");

        sname = findViewById(R.id.editText1);
        topic = findViewById(R.id.editText2);
        total = findViewById(R.id.editText3);
        correct = findViewById(R.id.editText4);
        incorrect = findViewById(R.id.editText5);

        mToolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Performance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void add_performance(View v)
    {
        if(!(TextUtils.isEmpty(sname.getText().toString())))
        {
            String id = databasePerformance.push().getKey();
            Sname = sname.getText().toString();
            Topic = topic.getText().toString();
            Total = Integer.parseInt(total.getText().toString());
            Correct = Integer.parseInt(correct.getText().toString());
            Incorrect = Integer.parseInt(incorrect.getText().toString());

            Performance performance=new Performance(Sname,Topic,Total,Correct,Incorrect);
            databasePerformance.child(Sname).setValue(performance);
            Toast.makeText(getApplicationContext(), "Performance Added Successfully", Toast.LENGTH_LONG).show();
        }
    }
}
