package com.example.pearlinfotech.HomeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.List_aboutus;
import com.example.pearlinfotech.Login.Login;
import com.example.pearlinfotech.R;

public class MainActivity extends AppCompatActivity {
Button loginh;
Button abouth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginh=findViewById(R.id.loginh);
        abouth=findViewById(R.id.abouth);

        loginh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        abouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, List_aboutus.class);
                startActivity(intent);
            }
        });
    }
}
