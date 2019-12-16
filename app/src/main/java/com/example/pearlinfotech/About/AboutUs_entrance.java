package com.example.pearlinfotech.About;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pearlinfotech.R;
public class          AboutUs_entrance extends AppCompatActivity
{
    Toolbar toolbar2;
    private RecyclerView recyclerView2;
    RecyclerView.LayoutManager layoutManager2;
    RecyclerViewAdapter recyclerViewAdapter1;

    int[] arr2={R.drawable.cat,R.drawable.cmat,R.drawable.gmat,R.drawable.gre,R.drawable.bca,R.drawable.mca,R.drawable.snap,R.drawable.nimcet,R.drawable.ibps,R.drawable.sbi,R.drawable.ielts};
    String[] arr3={"CAT","CMAT","GMAT","GRE","BCA","MCA","SNAP","NIMCET","IBPS","SBI PO","IELTS"};

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_entrance);
        recyclerView2=findViewById(R.id.recyclervieww);
        layoutManager2=new GridLayoutManager(this,2);
        recyclerView2.setLayoutManager(layoutManager2);

        toolbar2=findViewById(R.id.ftoolbar);
        toolbar2.setTitle("Entrance Exams");
        toolbar2.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        recyclerViewAdapter1 = new RecyclerViewAdapter(arr2,arr3);
        recyclerView2.setAdapter(recyclerViewAdapter1);
        recyclerView2.setHasFixedSize(true);
    }
}
