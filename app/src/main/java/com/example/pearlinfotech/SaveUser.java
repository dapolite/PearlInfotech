package com.example.pearlinfotech;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.Login.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SaveUser extends AppCompatActivity {
EditText un,ps;
Button save,retr;
TextView ret;
FirebaseFirestore db = FirebaseFirestore.getInstance();
DocumentReference mRef;
CollectionReference cRef=db.collection("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_save_user);
        save=findViewById(R.id.save);
    }
    public void LoadNote(View view) {
        retr = findViewById(R.id.retr);
        ret = findViewById(R.id.ret);
        String ft = ret.getText().toString();
        cRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot ds:queryDocumentSnapshots){
                    User user=ds.toObject(User.class);
                    String uname=user.getUsername();
                    String pass=user.getPassword();
                    if(uname.equals("dapo")) {
                        ret.setText(uname);
                    }
                    else{
                        Toast.makeText(SaveUser.this, "Wrong Username Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void saveData(View view){
        mRef = db.collection("users").document();
        un=findViewById(R.id.un);
        ps=findViewById(R.id.ps);
        String uname=un.getText().toString();
        String pass=ps.getText().toString();
        User user=new User(uname,pass);
        cRef.add(user);
    }
}
