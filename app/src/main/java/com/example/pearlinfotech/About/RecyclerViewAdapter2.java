package com.example.pearlinfotech.About;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pearlinfotech.R;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.MyViewHolder2>
{
    int[]arr4;
    String[] arr5;
    public RecyclerViewAdapter2(int[] arr4,String[] arr5)
    {
        this.arr4=arr4;
        this.arr5=arr5;
    }
    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view3,parent,false);
        RecyclerViewAdapter2.MyViewHolder2 myViewHolder2=new RecyclerViewAdapter2.MyViewHolder2(view);
        return myViewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter2.MyViewHolder2 holder, int position)
    {
        holder.imageView.setImageResource(arr4[position]);
        holder.textView.setText(arr5[position]);
    }

    @Override
    public int getItemCount()
    {
        return arr5.length;

    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;

        public MyViewHolder2(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView9);
            textView=itemView.findViewById(R.id.textView9);
        }
    }
}
