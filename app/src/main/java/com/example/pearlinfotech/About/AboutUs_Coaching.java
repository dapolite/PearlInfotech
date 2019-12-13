package com.example.pearlinfotech.About;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pearlinfotech.R;

public class AboutUs_Coaching extends AppCompatActivity
{
    Toolbar toolbar3;
    private RecyclerView recyclerView3;
    RecyclerView.LayoutManager layoutManager3;
    RecyclerViewAdapter recyclerViewAdapter2;

    int[] arr4={R.drawable.cbse,R.drawable.gseb};
    String[] arr5={"5-12","5-12"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us__coaching);
        recyclerView3=findViewById(R.id.recyclerviewww);
        layoutManager3=new GridLayoutManager(this,2);
        recyclerView3.setLayoutManager(layoutManager3);

        toolbar3=findViewById(R.id.ftoolbar);
        toolbar3.setTitle("Coaching ");

        recyclerViewAdapter2 = new RecyclerViewAdapter(arr4,arr5);
        recyclerView3.setAdapter(recyclerViewAdapter2);
        recyclerView3.setHasFixedSize(true);
    }
}
