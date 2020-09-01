package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Locale extends AppCompatActivity {

    // Button
    ImageButton done;
    Button button, getlocation, buttonlocation;

    // Date and Time
    EditText datefield, timefield;
    String vtype = "openbody";

    // data initialization for radio button
    String destination;
    String bookingtype = "single";
    String vsize = "mini";
    String vtime;
    int flag = 0;
    // user data
    EditText usrname, usremail, usraddress, usrphone, from, addname, addpicked;
    private int mYear, mMonth, mDay, mHour, mMinute;
    boolean enabled;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locale);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Fill Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        buttonlocation = findViewById(R.id.button);
        button = findViewById(R.id.pickbutton);
        done = findViewById(R.id.imageButton);
        getlocation = findViewById(R.id.getlocation);
        // user field
        usrname = findViewById(R.id.usrname);
        usremail = findViewById(R.id.usremail);
        usraddress = findViewById(R.id.usraddress);
        usrphone = findViewById(R.id.usrcontact);
        from = findViewById(R.id.from);
        //combination for destination
        addname = findViewById(R.id.namepicked);
        addpicked = findViewById(R.id.addresspicked);
        // date and time of booking
        datefield = findViewById(R.id.datetext);
        timefield = findViewById(R.id.timetext);
        // booking and vehicle type
        datefield = findViewById(R.id.datetext);
        timefield = findViewById(R.id.timetext);

        // firebaseauth and current user
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            // name.setText(user.getDisplayName().toString());
            usremail.setText(firebaseUser.getEmail());
            progressDialog = new ProgressDialog(Locale.this);
            getlocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.setMessage("Please Wait!!");
                    progressDialog.setMax(2);
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if (!enabled) {
                        showalertdialog();
                    }

                }
            });



            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if (!enabled) {
                        showalertdialog();
                    }

                }
            });
        }

    }

    public void openTACardView(View view) {
        destination = addname.getText().toString() + "," + addpicked.getText().toString();
        checknullfields();
        if (flag == 1) {
            Toast.makeText(this, "Fill Form Completely", Toast.LENGTH_SHORT).show();
            flag = 0;
        } else {


            BookingForm.uname = usrname.getText().toString();
            BookingForm.uemail = usremail.getText().toString();
            BookingForm.uaddress = usraddress.getText().toString();
            BookingForm.uphone = usrphone.getText().toString();

            BookingForm.from = from.getText().toString();
            // BookingForm.destination = usremail.getText().toString();
            BookingForm.destination = destination;
            BookingForm.timefield = timefield.getText().toString();
            BookingForm.datefield = datefield.getText().toString();
            // Boolean data
            BookingForm.bookingtype = bookingtype;
            BookingForm.vtype = vtype;
            BookingForm.vsize = vsize;
            Intent intent = new Intent(this, Agent.class);
            startActivity(intent);
        }
    }

    public void checknullfields() {
        if (TextUtils.isEmpty(from.getText().toString()))
            flag = 1;
        else if (TextUtils.isEmpty(destination))
            flag = 1;
        else if (TextUtils.isEmpty(usrname.getText().toString()))
            flag = 1;
        else if (TextUtils.isEmpty(usraddress.getText().toString()))
            flag = 1;
        else if (TextUtils.isEmpty(usremail.getText().toString()))
            flag = 1;
        else if (TextUtils.isEmpty(usrphone.getText().toString()))
            flag = 1;
        else if (TextUtils.isEmpty(datefield.getText().toString()))
            flag = 1;
        else if (TextUtils.isEmpty(timefield.getText().toString()))
            flag = 1;
    }


    public void openbody(View view) {
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.obody:
                if (checked)
                    vtype = "Open Body";
                break;
            case R.id.cbody:
                if (checked)
                    vtype = "Container";
                break;
            case R.id.trail:
                if (checked)
                    vtype = "Trailler";
                break;
        }

    }
    public void vsize (View view){
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.large:
                if (checked) {
                    vsize = "large";
                    vsize = "large";
                }
                break;
            case R.id.mini:
                if (checked) {
                    vsize = "mini";
                    vsize = "mini";
                }
                break;
            case R.id.oversize:
                if (checked) {
                    vsize = "oversize";
                    vsize = "oversize";
                }
                break;
        }
    }

    public void bookingtype (View view)
    {
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.single:
                if (checked) {
                    bookingtype = "Single";
                    bookingtype = "Single";
                }
                break;
            case R.id.round:
                if (checked) {
                    bookingtype = "Round";
                    bookingtype = "Round";
                }
        }
    }
    public void vtime (View view){
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.drop:
                if (checked)
                    vtime = "Drop-Down";
                Toast.makeText(this, "drop", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wait:
                if (checked) {
                    vtime = "Wait";
                    break;
                }
        }
    }

    // Date & Time
    public void dateenter(View view) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        datefield.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void timeset(View view) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

                        timefield.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void showalertdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Locale.this);
        builder.setTitle("Enable GPS");
        builder.setCancelable(false);
        builder.setMessage("Please enable GPS");
        builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                progressDialog.dismiss();
            }
        });
        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                progressDialog.dismiss();
            }
        });
        builder.create().show();
    }
}

