package com.example.pearlinfotech.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.Dashbard.DashboardFaculty;
import com.example.pearlinfotech.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {


Button loginbutton;
TextView fp;
String usern,pas;
    EditText uname,passw;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference mRef;
    Session session;
    CollectionReference cRef=db.collection("login");
    private FirebaseAuth mAuth;
    @Override

    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbutton = findViewById(R.id.loginbtn);
        session = new Session(Login.this);
        fp = findViewById(R.id.fp);
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgotPasssword.class);
                startActivity(intent);
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname=findViewById(R.id.uname);
                passw=findViewById(R.id.pass);
                usern=uname.getText().toString();
                pas=passw.getText().toString();
                cRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot ds:queryDocumentSnapshots){
                            User user=ds.toObject(User.class);
                            String uname=user.getUsername();
                            String pass=user.getPassword();
                            if(uname.equals(usern) && pass.equals(pas)) {
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("uname",uname); // value to store
                                editor.commit();
                                Intent intent=new Intent(Login.this, DashboardFaculty.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

    }
}
