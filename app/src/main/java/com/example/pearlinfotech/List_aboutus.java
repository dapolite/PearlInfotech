package com.example.pearlinfotech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.About.AboutUs;
import com.example.pearlinfotech.About.AboutUs_Coaching;
import com.example.pearlinfotech.About.AboutUs_entrance;

public class List_aboutus extends AppCompatActivity {
    Button but1,but2,but3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_aboutus);
        but1=findViewById(R.id.Entrancebut);
        but2=findViewById(R.id.Coachingbut);
        but3=findViewById(R.id.ITtrainingbut);

        but1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(List_aboutus.this, AboutUs_entrance.class);
                startActivity(intent);
            }
        });
        but2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(List_aboutus.this, AboutUs_Coaching.class);
                startActivity(intent);
            }
        });
        but3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(List_aboutus.this, AboutUs.class);
                startActivity(intent);
            }
        });
    }
}
