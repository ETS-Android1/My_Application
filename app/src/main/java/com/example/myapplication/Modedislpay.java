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
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Modedislpay extends AppCompatActivity {


    static EditText datefield, timefield;
    EditText autoCompleteTextViewfrom, name, email, address, phone;
    // Date and Time
    EditText txtDate, txtTime;
    String destination, vtype = "Open Body", bookingpref = "Single", vsize = "Mini", vtime = "Drop-Down";
    ImageButton done;

    static String to;
    int flag = 0;
    String vesize = "mini";
    Button getlocation, button;
    ArrayAdapter arrayAdapter;
    Spinner spinner;
    static int PERMISSION_REQUEST_CODE = 1;
    boolean enabled;
    ProgressDialog progressDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modedisp);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Enter Details");

        getlocation = findViewById(R.id.getlocation);
        autoCompleteTextViewfrom = findViewById(R.id.autoCompleteTextViewfrom);
        done = findViewById(R.id.imageButton);
        spinner = findViewById(R.id.spinner);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        button = findViewById(R.id.button);


        datefield = findViewById(R.id.datetext);
        timefield = findViewById(R.id.timetext);


        progressDialog = new ProgressDialog(Modedislpay.this);

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
              /*  else {
                    callPlaceDetectionApi();
                }*/
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
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
               /* else {
                    callPlaceDetectionApi();
                }*/
            }
        });

        Intent intent = getIntent();
        final String mode = intent.getStringExtra("mode");
    }

    public void openTACardView(View view) {
        checknullfields();
        if (flag == 1) {
            Toast.makeText(this, "Fill Form Completely", Toast.LENGTH_SHORT).show();
            flag = 0;
        } else {

            Intent i = new Intent(this, Agent.class);

            i.putExtra("name", name.getText().toString());
            i.putExtra("address", address.getText().toString());
            i.putExtra("email", email.getText().toString());
            i.putExtra("phone", phone.getText().toString());
            i.putExtra("from", autoCompleteTextViewfrom.getText().toString());
            i.putExtra("to", destination);
            i.putExtra("date", datefield.getText().toString());
            i.putExtra("time", timefield.getText().toString());
            i.putExtra("vtype", vtype);
            i.putExtra("bookingpref", bookingpref);
            i.putExtra("vsize", vsize);
            i.putExtra("vtime", vtime);

            startActivity(i);

            name.setText("");
            address.setText("");
            email.setText("");
            phone.setText("");
            autoCompleteTextViewfrom.setText("");
            datefield.setText("");
            timefield.setText("");
            flag = 0;
        }
    }


    public void showalertdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Modedislpay.this);
        builder.setTitle("Enable GPS");
        builder.setMessage("Please enable GPS");
        builder.setCancelable(false);
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

    public void openbody(View view) {
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.obody:
                if (checked)
                    vtype="Open Body";
                break;
            case R.id.cbody:
                if (checked)
                    vtype="Container";
                break;
            case R.id.trail:
                if (checked)
                    vtype="Trailler";
                break;
        }

    }
    public void vsize(View view) {
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.large:
                if (checked) {
                    vsize="large";
                    vsize="large";
                }
                break;
            case R.id.mini:
                if (checked){
                    vsize="mini";
                    vsize="mini";
                }
                break;
            case R.id.oversize:
                if (checked){
                    vsize="oversize";
                    vsize="oversize";
                }
                break;
        }
    }

    public void bookingtype(View view)
    {
        Boolean checked=((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.share:

                break;
            case R.id.individual:
                if(checked)
                    bookingpref="Individual";
                break;
        }
    }
    public void vtime(View view) {
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.drop:
                if (checked)
                    vtime = "Drop-Down";
                Toast.makeText(this, "drop", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wait:
                if (checked) {
                    alert();
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

    public void alert(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        alert.setTitle("Wait-Hours");
        alert.setMessage("Enter wait Hours for cab:");
        final EditText editText=new EditText(this);
        editText.setHint("Enter no. of hours");
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(editText);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    String waithours=editText.getText().toString();

                if(waithours.equals("")){
                    alert();
                }
                else if(Integer.parseInt(waithours)>24||Integer.parseInt(waithours)==0){
                        Toast.makeText(getApplicationContext(),"Wait Hours should be less than a day..",Toast.LENGTH_SHORT).show();
                        alert();
                    }
                else {
                    vtime = "Wait For " + editText.getText().toString() + " hours";
                }
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alert();
            }
        });
        alert.create().show();
    }


    public void checknullfields() {
        if (TextUtils.isEmpty(autoCompleteTextViewfrom.getText().toString()))
            flag = 1;
        else if (TextUtils.isEmpty(destination))
            flag = 1;
        else if (TextUtils.isEmpty(name.getText().toString()))
            flag = 1;
        else if (TextUtils.isEmpty(address.getText().toString()))
            flag = 1;
        else if (TextUtils.isEmpty(email.getText().toString()))
            flag = 1;
        else if (TextUtils.isEmpty(phone.getText().toString()))
            flag = 1;
        else if (TextUtils.isEmpty(datefield.getText().toString()))
            flag = 1;
        else if (TextUtils.isEmpty(timefield.getText().toString()))
            flag = 1;
    }


}

