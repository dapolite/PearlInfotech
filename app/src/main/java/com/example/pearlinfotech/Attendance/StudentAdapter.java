package com.example.pearlinfotech.Attendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pearlinfotech.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


public class StudentAdapter extends  FirestoreRecyclerAdapter<StudentItem, StudentAdapter.StudentViewHolder> {
    private   OnItemClickListener listener;
    public StudentAdapter(@NonNull FirestoreRecyclerOptions<StudentItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull StudentViewHolder holder, int position, @NonNull StudentItem model) {
        holder.studname.setText(model.getStudent_name());
        holder.course.setText(model.getCourse_name());
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist,
                parent, false);
        return new StudentViewHolder(v);
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView studname;
        public TextView course;

        public StudentViewHolder(View itemView) {
            super(itemView);
            studname = itemView.findViewById(R.id.studnameatdf);
            course = itemView.findViewById(R.id.coursenameatdf);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(pos), pos);
                    }
                }
            });
        }

    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot,int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
