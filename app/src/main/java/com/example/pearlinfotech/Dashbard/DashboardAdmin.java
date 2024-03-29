package com.example.pearlinfotech.Dashbard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.example.pearlinfotech.Admin.FacultyList;
import com.example.pearlinfotech.Admin.StudentList;
import com.example.pearlinfotech.Fees.fees_admin;
import com.example.pearlinfotech.HomeScreen.MainActivity;
import com.example.pearlinfotech.Message.MessageSend;
import com.example.pearlinfotech.R;
import com.tfb.fbtoast.FBToast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardAdmin extends AppCompatActivity {
    private static long back_pressed;
    CardView cv1,cv2,cv3,fee_admin,cv4;
    Bundle basket = new Bundle();
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        Toolbar toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle("Admin Dashboard : "+"("+date+")");
        toolbar1.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cv1=findViewById(R.id.facultyacard);
        fee_admin=findViewById(R.id.feesdashacard);
        fee_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i=new Intent(DashboardAdmin.this, fees_admin.class);
                startActivity(i);
            }
        });
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DashboardAdmin.this, FacultyList.class);
                startActivity(i);
            }
        });
        cv2=findViewById(R.id.feesdashacard);
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DashboardAdmin.this, fees_admin.class);
                startActivity(i);
            }
        });
        cv3=findViewById(R.id.studdashacard);
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DashboardAdmin.this, StudentList.class);
                startActivity(i);
            }
        });
        cv4=findViewById(R.id.notifdashacard);
        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DashboardAdmin.this, MessageSend.class);
                basket.putString("tid", "admin");
                i.putExtras(basket);
                startActivity(i);


            }
        });



    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout(MenuItem item) {

        Intent logout=new Intent(DashboardAdmin.this, MainActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        SharedPreferences sharedpreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        startActivity(logout);

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
