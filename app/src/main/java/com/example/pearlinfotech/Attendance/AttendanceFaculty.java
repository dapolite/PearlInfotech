package com.example.pearlinfotech.Attendance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pearlinfotech.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tfb.fbtoast.FBToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AttendanceFaculty extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener date1;
    String teacher_id;
    String class_selected;
    EditText adate;
    ArrayList<String> selectedItems;
    ArrayList<String> nonselectedItems;
    ArrayAdapter<String> aa;
    ArrayList<String> ul;
    EditText dateat;
    ListView listView;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Usernames = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbTeacher;
    DatabaseReference stuAttendance;
    DatabaseReference dbStudent;
    String dates;
    Calendar myCalendar= Calendar.getInstance();
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_faculty);
        adate=findViewById(R.id.attdate);
        Toolbar toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle("Mark Attendance");
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
        ref = FirebaseDatabase.getInstance().getReference();
        dbStudent = ref.child("Student");
        Bundle bundle1 = getIntent().getExtras();
        teacher_id = bundle1.getString("tid");
        class_selected=bundle1.getString("class_selected");
        dbAttendance = ref.child("Attendance");
        dbTeacher=ref.child("Faculty");
        dbTeacher.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                class_selected=dataSnapshot.child(teacher_id).child("classes").getValue().toString();
                Log.d("TAG",class_selected);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);
            }
        });
        selectedItems = new ArrayList<String>();
        dates=adate.getText().toString();
        TextView classname = findViewById(R.id.textView);

        date1  = new DatePickerDialog.OnDateSetListener(){    @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }};
        adate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AttendanceFaculty.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        classname.setText(class_selected);


        DatabaseReference dbuser = ref.child("Student");

        dbuser.orderByChild("classes").equalTo(class_selected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(dsp.child("sid").getValue().toString());
                    Usernames.add(dsp.child("sid").getValue().toString());


                }
                OnStart(Userlist);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                FBToast.warningToast(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG);
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
                FBToast.successToast(this, "Attendance created Successfully", Toast.LENGTH_SHORT);
                nonselectedItems.remove(item);
                dbAttendance.child(item).setValue("Present");
                stuAttendance=ref.child("StudentAttendance").child(item);
                stuAttendance.child(dates).setValue("Present");
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
    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        adate.setText(sdf.format(myCalendar.getTime()));
    }
}


