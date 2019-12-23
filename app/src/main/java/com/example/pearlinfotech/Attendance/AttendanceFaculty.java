package com.example.pearlinfotech.Attendance;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
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

@SuppressWarnings("unchecked")
public class AttendanceFaculty extends AppCompatActivity implements SearchView.OnQueryTextListener {
    DatePickerDialog.OnDateSetListener date1;
    String teacher_id;
    private SearchView searchView;
    private MenuItem searchMenuItem;
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
    String atdates;
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                FBToast.errorToast(getApplicationContext(), "Database Error", FBToast.LENGTH_LONG);
            }
        });
        selectedItems = new ArrayList<String>();
        TextView classname = findViewById(R.id.textView);

        date1  = new DatePickerDialog.OnDateSetListener(){    @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
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
            atdates=adate.getText().toString();

            dbAttendance = ref.child("Attendance/"+atdates).child(class_selected).child(teacher_id);

            for (String item : selectedItems) {
                Log.d("TAGSS",atdates);
                Log.d("TAGSS",item);
                Log.d("TAGSS",teacher_id);
                nonselectedItems.remove(item);
                dbAttendance.child(item).setValue("Present");
                stuAttendance=ref.child("StudentAttendance/"+item+"/"+atdates);
                stuAttendance.setValue("Present");
                aa.notifyDataSetChanged();
                if (selItems == "") {
                    selItems = item;
                    Log.d("TAGSS",item);
                    Log.d("TAGSS",teacher_id);
                }
                else {
                    selItems += "/" + item;
                    Log.d("TAGSS",item);
                    Log.d("TAGSS",teacher_id);
                }
            }
            for (String item : nonselectedItems) {


                dbAttendance.child(item).setValue("Absent");
                stuAttendance=ref.child("StudentAttendance/"+item);
                stuAttendance.child(atdates).setValue("Absent");
                aa.notifyDataSetChanged();
                Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
            }
        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        adate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        aa.getFilter().filter(newText);
        return true;
    }
}


