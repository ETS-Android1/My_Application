package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

    Button button;
    Button getlocation, buttonlocation;
    EditText datefield;
    EditText timefield;
    String destination, vtype = "Open Body", bookingtype = "Single", vsize = "Mini", vtime = "Drop-Down";
    int flag = 0;
    String vesize = "mini";
    ImageButton done;
    EditText uname, uaddress, from, name, email, address, phone;
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

        button = (Button) findViewById(R.id.pickbutton);
        uname = (EditText) findViewById(R.id.namepicked);
        uaddress = (EditText) findViewById(R.id.addresspicked);
        from = (EditText) findViewById(R.id.autoCompleteTextViewfrom);
        getlocation = (Button) findViewById(R.id.getlocation);

        done = (ImageButton) findViewById(R.id.imageButton);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone);
        buttonlocation = (Button) findViewById(R.id.button);

        datefield = (EditText) findViewById(R.id.datetext);
        timefield = (EditText) findViewById(R.id.timetext);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();


        if (firebaseUser != null) {
//            name.setText(user.getDisplayName().toString());
            email.setText(firebaseUser.getEmail().toString());


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

            buttonlocation.setOnClickListener(new View.OnClickListener() {
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
        public void openTACardView(View view){
            destination = uname.getText().toString() + "," + uaddress.getText().toString();
            //  checknullfields();
            //    if(flag==1) {
            //Toast.makeText(this, "Fill Form Completely", Toast.LENGTH_SHORT).show();
            //flag = 0;
            //  }
            //   else {
            Intent i = new Intent(this, Agent.class);

            Bundle bundle = new Bundle();
            bundle.putString("name", name.getText().toString());
            bundle.putString("address", address.getText().toString());
            bundle.putString("email", email.getText().toString());
            bundle.putString("phone", phone.getText().toString());
            bundle.putString("from", from.getText().toString());
            bundle.putString("to", destination);
            bundle.putString("date", datefield.getText().toString());
            bundle.putString("time", timefield.getText().toString());
            bundle.putString("vtype", vtype);
            bundle.putString("bookingpref", bookingtype);
            bundle.putString("vsize", vsize);
            bundle.putString("vtime", vtime);
            i.putExtras(bundle);
            startActivity(i);

            name.setText("");
            address.setText("");
            email.setText("");
            phone.setText("");
            uname.setText("");
            uaddress.setText("");
            from.setText("");
            datefield.setText("");
            timefield.setText("");
            flag = 0;

            Intent intent = new Intent(this, Agent.class);
            startActivity(intent);
        }

/*
        public void checknullfields () {
            if (TextUtils.isEmpty(from.getText().toString()))
                flag = 1;
            else if (TextUtils.isEmpty(destination.toString().toString()))
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
*/


    public void openbody (View view){
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

                break;
            case R.id.round:
                if (checked)
                    bookingtype = "Return";
                break;
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
                    vtime="Wait";
                    break;
                }
        }
    }
    public void dateenter (View view){
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        DatePicker.flag = 1;
        DatePicker datePicker = new DatePicker();
        datePicker.show(getSupportFragmentManager(), "date");
    }
    public void timeset (View view){
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        TimePicker.flag = 1;
        TimePicker timePicker = new TimePicker();
        timePicker.show(getSupportFragmentManager(), "time");

    }

    public void showalertdialog(){
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

