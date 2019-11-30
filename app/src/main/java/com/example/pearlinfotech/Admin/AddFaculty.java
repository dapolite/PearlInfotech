package com.example.pearlinfotech.Admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddFaculty extends AppCompatActivity {
    EditText Tname;
    EditText Tid;
    EditText subject, tpassword;
    String tname, tid, sub, classname, tpass;
    Spinner classes;
    Button addButton;
    DatabaseReference databaseTeacher;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        databaseTeacher = FirebaseDatabase.getInstance().getReference("Faculty");

        Tname = findViewById(R.id.editText1);
        Tid = findViewById(R.id.editText3);
        subject = findViewById(R.id.editText4);
        classes = findViewById(R.id.spinner3);
        tpassword = findViewById(R.id.editText5);
        mToolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add/Remove Teacher");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void addTeacher(View v) {
        tname = Tname.getText().toString();
        tid = Tid.getText().toString();
        sub = subject.getText().toString();
        classname = classes.getSelectedItem().toString();
        tpass = tpassword.getText().toString();

        if (!(TextUtils.isEmpty(Tid.getText().toString()))) {
            String id = databaseTeacher.push().getKey();
            Faculty teacher = new Faculty(tname, tid, sub, classname, tpass);
            databaseTeacher.child(tid).setValue(teacher);
            Toast.makeText(getApplicationContext(), "Teacher added successfully", Toast.LENGTH_LONG).show();
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "fields cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    public void removeTeacher(View v) {
        if (!TextUtils.isEmpty(Tid.getText().toString())) {
            tid = Tid.getText().toString();
            databaseTeacher.child(tid).setValue(null);
            Toast.makeText(getApplicationContext(), "Teacher removed successfully", Toast.LENGTH_LONG).show();
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "id cannot be empty", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
