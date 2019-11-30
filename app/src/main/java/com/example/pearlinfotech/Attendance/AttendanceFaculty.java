package com.example.pearlinfotech.Attendance;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AttendanceFaculty extends AppCompatActivity {
    String teacher_id;
    String class_selected;
    Spinner period;
    String periodno;
    ArrayList<String> selectedItems;
    ArrayList<String> nonselectedItems;
    Toolbar mToolbar;

    ArrayList<String> ul;
    ListView listView;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Usernames = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_faculty);
//      mToolbar = findViewById(R.id.takeattendancebar);
        period = findViewById(R.id.spinner4);
        ref = FirebaseDatabase.getInstance().getReference();
        dbStudent = ref.child("Student");
        dbAttendance = ref.child("attendance");

        dbStudent.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sid,P1="-",P2="-",P3="-",P4="-",P5="-",P6="-",P7="-",P8="-";
                Attendance_sheet a = new Attendance_sheet(P1,P2,P3,P4,P5,P6,P7,P8);

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    sid=dsp.child("sid").getValue().toString();
                    dbAttendance.child(date).child(sid).setValue(a);

                }
                Toast.makeText(getApplicationContext(),"successfully created "+date+" db", Toast.LENGTH_LONG).show();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something wrong", Toast.LENGTH_LONG).show();
            }

        });

        selectedItems = new ArrayList<String>();

        TextView classname = findViewById(R.id.textView);
        classname.setText("CSE");
        Bundle bundle1 = getIntent().getExtras();
        class_selected = bundle1.getString("class_selected");
        teacher_id = bundle1.getString("tid");
        //  Toast.makeText(getApplicationContext(), teacher_id, Toast.LENGTH_LONG).show();

        classname.setText(class_selected);


        DatabaseReference dbuser = ref.child("Student");

        dbuser.orderByChild("classes").equalTo(class_selected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(dsp.child("sid").getValue().toString());
                    Usernames.add(dsp.child("sname").getValue().toString());


                }
                OnStart(Userlist);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void OnStart(ArrayList<String> userlist) {
        nonselectedItems = userlist;
        ListView chl = findViewById(R.id.checkable_list);
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.checkable_list_layout, R.id.txt_title, userlist);
        chl.setAdapter(aa);
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selectedItem))
                    selectedItems.remove(selectedItem);
                else
                    selectedItems.add(selectedItem);

            }

        });


    }

    public void showSelectedItems(View view) {
        String selItems = "";
        periodno = period.getSelectedItem().toString();
        if (periodno.equals("Select Period")) {
            Toast.makeText(this, "Select a class", Toast.LENGTH_LONG).show();

        } else {
            ref = FirebaseDatabase.getInstance().getReference();

            dbAttendance = ref.child("attendance").child(date);

            for (String item : selectedItems) {
                Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
                nonselectedItems.remove(item);
                dbAttendance.child(item).child(periodno).setValue("P" + " / " + teacher_id);
                if (selItems == "")
                    selItems = item;
                else
                    selItems += "/" + item;
            }
            // Toast.makeText(this, selItems, Toast.LENGTH_LONG).show();


            //for  absent
            for (String item : nonselectedItems) {
                Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
                dbAttendance.child(item).child(periodno).setValue("A" + " / " + teacher_id);
                //Toast.makeText(this, "absentees:" + nonselectedItems, Toast.LENGTH_LONG).show();

            }
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

class Attendance_sheet {
    String p1,p2,p3,p4,p5,p6,p7,p8;

    public Attendance_sheet(String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8) {
        this.p1=p1;
        this.p2=p2;
        this.p3=p3;
        this.p4=p4;
        this.p5=p5;
        this.p6=p6;
        this.p7=p7;
        this.p8=p8;

    }

    public String getP1() {
        return p1;
    }
    public String getP2() {
        return p2;
    }
    public String getP3() {
        return p3;
    }
    public String getP4() {
        return p4;
    }
    public String getP5() { return p5; }
    public String getP6() {
        return p6;
    }
    public String getP7() {
        return p7;
    }
    public String getP8() {
        return p8;
    }
}

