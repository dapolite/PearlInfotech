package com.example.pearlinfotech;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ForgotPasssword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passsword);
        ActionBar bar = getSupportActionBar();
        if(bar!=null){
            bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg1));
        }
    }
}
