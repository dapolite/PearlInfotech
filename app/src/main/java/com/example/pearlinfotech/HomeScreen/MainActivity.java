package com.example.pearlinfotech.HomeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pearlinfotech.About.List_aboutus;
import com.example.pearlinfotech.Login.Login;
import com.example.pearlinfotech.R;
import com.tfb.fbtoast.FBToast;

public class MainActivity extends AppCompatActivity {
Button loginh;
Button abouth;
    private static long back_pressed;
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

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }
        else {
            FBToast.infoToast(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT);
            back_pressed = System.currentTimeMillis();
        }
    }


}
