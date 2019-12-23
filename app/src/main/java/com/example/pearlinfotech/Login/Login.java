package com.example.pearlinfotech.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pearlinfotech.Admin.CheckNetwork;
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
import com.tfb.fbtoast.FBToast;

import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ValidateTor validateTor = new ValidateTor();

    private static long back_pressed;
    TextView fg;
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

        fg=findViewById(R.id.fp);
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

        fg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,ForgotPasssword.class);
                startActivity(intent);
            }
        });

    }


    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }


    public void onButtonClick(View v) {

        if (CheckNetwork.isInternetAvailable(Login.this)) {
            userid = username.getText().toString();
            pass = password.getText().toString();

            if (validateTor.isEmpty(pass)) {
                password.setError("field Empty");
            }
            if (validateTor.isEmpty(userid)) {
                username.setError("field Empty");
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
                        FBToast.errorToast(Login.this, "Something went wrong", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    FBToast.errorToast(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG);
                }
            });
        }
        else{
            FBToast.warningToast(Login.this, "No Internet Connection", FBToast.LENGTH_SHORT);
        }
    }


    public void verify(String dbpassword) {
        if (validateTor.isEmpty(userid)) {
            username.setError("Field is empty!");
        }else if (item.equals("Faculty") && pass.equalsIgnoreCase(this.dbpassword)) {

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
            password.setError("Username and Password Incorrect");

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
            FBToast.infoToast(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT);
            back_pressed = System.currentTimeMillis();
        }
    }


}