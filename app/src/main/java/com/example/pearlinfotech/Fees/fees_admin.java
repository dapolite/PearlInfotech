package com.example.pearlinfotech.Fees;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pearlinfotech.R;

public class fees_admin extends AppCompatActivity
{
    EditText e1,e2,e3,e4;
    Spinner spin1,spin2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_admin);

        spin1=findViewById(R.id.feeadmininstallment);
        spin2=findViewById(R.id.feeadminmode);

        e1=findViewById(R.id.editText1);
        e1=findViewById(R.id.editText3);
        e1=findViewById(R.id.editText4);
        e1=findViewById(R.id.editText5);


    }
}
