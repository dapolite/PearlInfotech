package com.example.pearlinfotech.Admin;


import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddStudent extends AppCompatActivity {
    EditText Sname;
    EditText Sid, spassword;
    String sname, sid, classname, spass;
    Spinner classes;
    String edts;
    DatabaseReference databaseStudent;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        databaseStudent = FirebaseDatabase.getInstance().getReference("Student");

        Sname = findViewById(R.id.editText1);
        Sid = findViewById(R.id.editText3);
        classes = findViewById(R.id.spinner3);
        spassword = findViewById(R.id.editText4);
        mToolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add/Remove Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void addStudent(View v) {


        if (!(TextUtils.isEmpty(Sid.getText().toString()))) {
            String id = databaseStudent.push().getKey();
            sname = Sname.getText().toString();
            sid = Sid.getText().toString();
            classname = classes.getSelectedItem().toString();
            spass = spassword.getText().toString();

            Student student = new Student(sname, sid, classname, spass);
            databaseStudent.child(sid).setValue(student);
            Toast.makeText(getApplicationContext(), "student added successfully", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "fields cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    public void removeStudent(View v) {
        if (!TextUtils.isEmpty(Sid.getText().toString()))
        {
            //sid = Sid.getText().toString();
            //databaseStudent.child(sid).setValue(null);
            //Toast.makeText(getApplicationContext(), "teacher removed successfully", Toast.LENGTH_LONG).show();

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.edit_text_dialog, null);
            dialogBuilder.setView(dialogView);

            final EditText edt =  dialogView.findViewById(R.id.edittdialog);
            edts=edt.getText().toString();
            dialogBuilder.setTitle("Remove Student");
            //dialogBuilder.setMessage("Enter Student ID :- ");
            dialogBuilder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    //databaseStudent.child("Student").child(edts).removeValue();
                    Log.d("TAG",edts);
                }
            });
            dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    //pass
                }
            });
            AlertDialog b = dialogBuilder.create();
            b.show();
        }
        else
            {
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