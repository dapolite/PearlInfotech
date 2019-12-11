package com.example.pearlinfotech.About;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pearlinfotech.R;

public class RecyclerViewAdapter1 extends RecyclerView.Adapter<RecyclerViewAdapter1.MyViewHolder1>
{
    int[]arr2;
    String[] arr3;
    public RecyclerViewAdapter1(int[] arr2,String[] arr3)
    {
        this.arr2=arr2;
        this.arr3=arr3;
    }
    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view2,parent,false);
        MyViewHolder1 myViewHolder1=new MyViewHolder1(view);
        return myViewHolder1;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position)
    {
        holder.imageView.setImageResource(arr2[position]);
        holder.textView.setText(arr3[position]);
    }

    @Override
    public int getItemCount()
    {
        return arr3.length;

    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;

        public MyViewHolder1(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView99);
            textView=itemView.findViewById(R.id.textView99);
        }
    }
}
