package com.example.pearlinfotech.About;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pearlinfotech.R;

public class AboutUs extends AppCompatActivity
{
    Toolbar toolbar1;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    int[] arr={R.drawable.java1,R.drawable.python,R.drawable.langc,R.drawable.cpp,R.drawable.sql,R.drawable.mysql,R.drawable.oracle,R.drawable.php,R.drawable.js,R.drawable.jq,R.drawable.android,R.drawable.asp,R.drawable.bootstrap,R.drawable.dj,R.drawable.htmll,R.drawable.css,R.drawable.visual,R.drawable.tally,R.drawable.xcel};
    String[] arry={"Java","Python","C","C++","SQL","MySql","Oracle","php","JavaScript","JQuery","Android","Asp.net","Bootstrap","Django","HTML","CSS","VB.net","Tally","Excel"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle("IT Subject");
        //t

        recyclerViewAdapter = new RecyclerViewAdapter(arr,arry);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
    }
}
