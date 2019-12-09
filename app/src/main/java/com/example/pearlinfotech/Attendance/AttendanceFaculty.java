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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    ArrayAdapter<String> aa;
    ArrayList<String> ul;
    ListView listView;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Usernames = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference stuAttendance;
    DatabaseReference dbStudent;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_faculty);
        Toolbar toolbar = findViewById(R.id.ftoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Take Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ref = FirebaseDatabase.getInstance().getReference();
        dbStudent = ref.child("Student");
        dbAttendance = ref.child("attendance");
        selectedItems = new ArrayList<String>();

        TextView classname = findViewById(R.id.textView);
        classname.setText("CSE");
        Bundle bundle1 = getIntent().getExtras();
        class_selected = bundle1.getString("class_selected");
        teacher_id = bundle1.getString("tid");

        classname.setText(class_selected);


        DatabaseReference dbuser = ref.child("Student");

        dbuser.orderByChild("classes").equalTo(class_selected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(dsp.child("sname").getValue().toString());
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
        return true;
    }

    public void OnStart(ArrayList<String> userlist) {
        nonselectedItems = userlist;
        ListView chl = findViewById(R.id.checkable_list);
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

         aa = new ArrayAdapter<String>(this, R.layout.checkable_list_layout, R.id.txt_title, userlist);
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
            ref = FirebaseDatabase.getInstance().getReference();

            dbAttendance = ref.child("Attendance").child(date).child(class_selected).child(teacher_id);

            for (String item : selectedItems) {
                Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
                nonselectedItems.remove(item);
                dbAttendance.child(item).setValue("Present");
                stuAttendance=ref.child("StudentAttendance").child(item);
                stuAttendance.child(date).setValue("Present");
                aa.notifyDataSetChanged();
                if (selItems == "")
                    selItems = item;
                else
                    selItems += "/" + item;
            }
            for (String item : nonselectedItems) {
                Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
                dbAttendance.child(item).setValue("Absent");
                stuAttendance=ref.child("StudentAttendance").child(item);
                stuAttendance.child(date).setValue("Absent");
                aa.notifyDataSetChanged();

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


