package com.example.pearlinfotech.Fees;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fees_admin extends AppCompatActivity
{
    String sname,course,type1,type2;
    int total,paid;
    EditText e1,e2,e3,e4,e5;
    Spinner spin1,spin2;
    Toolbar mToolbar;
    DatabaseReference databaseFees;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_admin);

        spin1=findViewById(R.id.feeadmininstallment);
        spin2=findViewById(R.id.feeadminmode);

        e1=findViewById(R.id.editText1);
        e3=findViewById(R.id.editText3);
        e4=findViewById(R.id.editText4);
        e5=findViewById(R.id.editText5);

        databaseFees = FirebaseDatabase.getInstance().getReference("Fees");
        mToolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Fee Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void addFees(View v) {


        if (!(TextUtils.isEmpty(e1.getText().toString()))) {
            String id = databaseFees.push().getKey();
            sname = e1.getText().toString();
            total =Integer.parseInt( e3.getText().toString());
            course = e4.getText().toString();
            paid = Integer.parseInt( e5.getText().toString());
            type1=spin1.getSelectedItem().toString();
            type2=spin2.getSelectedItem().toString();

            Fee fee = new Fee(sname, total, paid,course,type1,type2);
            databaseFees.child(sname).setValue(fee);
            Toast.makeText(getApplicationContext(), "Fees added successfully", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "fields cannot be empty", Toast.LENGTH_LONG).show();
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
