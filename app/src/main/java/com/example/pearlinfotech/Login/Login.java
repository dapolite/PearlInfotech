package com.example.pearlinfotech.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pearlinfotech.Dashbard.DashBoardStudent;
import com.example.pearlinfotech.Dashbard.DashboardAdmin;
import com.example.pearlinfotech.Dashbard.DashboardFaculty;
import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raywenderlich.android.validatetor.ValidateTor;

import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ValidateTor validateTor = new ValidateTor();

    private static long back_pressed;
    EditText username, password;
    String item;
    String userid, pass;
    DatabaseReference ref;
    String dbpassword;
    Bundle basket;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.uname);
        password = findViewById(R.id.pass);

        Spinner spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("Admin");
        categories.add("Faculty");
        categories.add("Student");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }


    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }


    public void onButtonClick(View v) {


        userid = username.getText().toString();
        pass = password.getText().toString();
        if (validateTor.isEmpty(userid)) {
            username.setError("Field is empty!");
        }
        if (validateTor.isEmpty(pass)) {
            password.setError("Field is empty!");
        }
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please Wait..." + userid);
        mDialog.setTitle("Loading");
        mDialog.show();
        basket = new Bundle();
        basket.putString("message", userid);

        ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dbuser = ref.child(item).child(userid);

        dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dbchild = null;
                try {
                    if (item.equals("Admin")) {
                        mDialog.dismiss();
                        dbpassword = dataSnapshot.getValue(String.class);
                        verify(dbpassword);


                    } else {
                        mDialog.dismiss();
                        if (item.equals("Student")) {
                            dbchild = "spass";
                        }
                        if (item.equals("Faculty")) {
                            dbchild = "tpass";
                        }

                        dbpassword = dataSnapshot.child(dbchild).getValue(String.class);
                        verify(dbpassword);
                    }
                } catch (Exception e) {
                    Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        });
        Toast.makeText(getApplicationContext(), dbpassword, Toast.LENGTH_LONG).show();
    }


    public void verify(String dbpassword) {
        if (userid.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Username cannot be empty", Toast.LENGTH_LONG).show();
        } else if (item.equals("Faculty") && pass.equalsIgnoreCase(this.dbpassword)) {

            mDialog.dismiss();
            Intent intent = new Intent(this, DashboardFaculty.class);
            intent.putExtras(basket);
            startActivity(intent);

        } else if (item.equals("Admin") && pass.equalsIgnoreCase(this.dbpassword)) {
            mDialog.dismiss();
            Intent intent = new Intent(this, DashboardAdmin.class);
            intent.putExtras(basket);
            startActivity(intent);
        } else if (item.equals("Student") && pass.equalsIgnoreCase(this.dbpassword)) {
            mDialog.dismiss();
            Intent intent = new Intent(this, DashBoardStudent.class);
            intent.putExtras(basket);
            startActivity(intent);
        } else if (!pass.equalsIgnoreCase(this.dbpassword)) {
            Toast.makeText(getApplicationContext(), "UserId or Password is Incorrect", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }


}