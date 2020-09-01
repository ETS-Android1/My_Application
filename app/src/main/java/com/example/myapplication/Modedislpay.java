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
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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


    EditText autoCompleteTextViewfrom,name,email,address,phone;
    static EditText datefield,timefield;
    ImageButton done;

    static String to;

    String destination,vtype="Open Body",bookingpref="Single",vsize="Mini",vtime="Drop-Down";
    int flag=0;

    String vesize="mini";
    ArrayAdapter arrayAdapter;
    Spinner spinner;
    static int PERMISSION_REQUEST_CODE = 1;
    boolean enabled;
    ProgressDialog progressDialog;
    Button getlocation,button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modedisp);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Enter Details");

        getlocation=(Button)findViewById(R.id.getlocation);
        autoCompleteTextViewfrom = (EditText) findViewById(R.id.autoCompleteTextViewfrom);
        done=(ImageButton)findViewById(R.id.imageButton);
        spinner=(Spinner)findViewById(R.id.spinner);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        address=(EditText)findViewById(R.id.address);
        phone=(EditText)findViewById(R.id.phone);
        button=(Button)findViewById(R.id.button);


        datefield=(EditText)findViewById(R.id.datetext);
        timefield=(EditText)findViewById(R.id.timetext);


        progressDialog=new ProgressDialog(Modedislpay.this);

        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please Wait!!");
                progressDialog.setMax(2);
                progressDialog.setCancelable(true);
                progressDialog.show();
                LocationManager locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
                LocationManager locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!enabled) {
                    showalertdialog();
                }
               /* else {
                    callPlaceDetectionApi();
                }*/
            }
        });

        Intent intent=getIntent();
        final String mode=intent.getStringExtra("mode");

      if(mode.equals("Railways")){
            if(to.equals("Hyderabad")){
                String[]name={"Station Name:\tHyderabad Deccan railway station\nStation code:\tHYB"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Bangalore")){
                String[]name={"Station Name:\tBangalore City railway station\nStation code:\tSBC"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Tirupati")){
                String[]name={"Station Name:\tTirupati railway station\n" +"Station code:\tTPTY"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }

            else if(to.equals("Coimbatore")){
                String[]name={"Station Name:\tCoimbatore Junction railway station\n" +"Station code:\tCBE"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Madurai")){
                String[]name={"Station Name:\tMadurai Junction railway station\n" +"Station code:\tMDU"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Pondicherry")){
                String[]name={"Station Name:\tPuducherry\n" +"Station code:\tPDY"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Vellore")){
                String[]name={"Station Name:\tKatpadi Junction\n" +"Station code:\tKPD"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }else if(to.equals("Kodaikanal")){
                String[]name={"Station Name:\tKodaikanal Road railway station\n" +"Station code:\tKQN"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }else if(to.equals("Ooty")){
                String[]name={"Station Name:\tUdhagamandalam\n" +"Station code:\tUAM"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Erode")){
                String[]name={"Station Name:\tErode Junction\n" +"Station code:\tED"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Rameshwaram")){
                String[]name={"Station Name:\tRameswaram\n" +"Station code:\tRMM"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
        }


        //######################################## OPENING LIST ACTIVITY#################################################

        else if(mode.equals("Airport")){
            if(to.equals("Hyderabad")){
                String[]name={"Airport Name:\tRajiv Gandhi International Airport\nAirport code:\tHYD"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Bangalore")){
                String[]name={"Airport Name:\tKempegowda International Airport\nAirport code:\tBLR"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Tirupati")){
                String[]name={"Airport Name:\tTTirupati airport\nAirport code:\tTIR"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Chennai")){
                String [] name={"Airport Name:\tChennai International Airport\nAirport code:\tMAA"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }

            else if(to.equals("Coimbatore")){
                String[]name={"Airport Name:\tCoimbatore International Airport\nAirport code:\tCJB"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Madurai")){
                String[]name={"Airport Name:\tMadurai Airport\nAirport code:\tIXM"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Pondicherry")){
                String[]name={"Airport Name:\tPuducherry Airport"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Vellore")){
                String[]name={"No Available Airports!\nPlease Select Another City.\n" +
                        " Nearest Airports:\n1.Chennai Airport\n" +
                        "2.Banglore Airport"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }else if(to.equals("Kodaikanal")){
                String[]name={"No Available Airports!\nPlease Select Another City.\n Nearest Airports:\n1.Madurai Airport\n2.Coimbatore airport"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }else if(to.equals("Ooty")){
                String[]name={"No Available Airports!\nPlease Select Another City.\nNearest Airports:\nCoimbatore Airport)"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Erode")){
                String[]name={"No Available Airports!\nPlease Select Another City.\n" +
                        "Nearest Airports:\n1.Coimbatore Airport)"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
            else if(to.equals("Rameshwaram")){
                String[]name={"No Available Airports!\nPlease Select Another City.\n" +
                        "Nearest Airports:\n1.Madurai Airport)"};
                arrayAdapter=new ArrayAdapter(this,R.layout.row_layout,R.id.rowlayouttext,name);
            }
        }


       spinner.setAdapter(arrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("spinner",adapterView.getItemAtPosition(i).toString());
                if((mode.equals("Airways")&&(to.equals("Rameshwaram")||to.equals("Erode")||to.equals("Ooty")||to.equals("Kodaikanal")||to.equals("Vellore"))))
                    Toast.makeText(getApplicationContext(),"Please Select Specified City and then try...",Toast.LENGTH_SHORT).show();
                else
                {
                    if(to.toString().equals("Chennai")&&mode.equals("Railways"))
                        destination=adapterView.getItemAtPosition(i).toString()+" Railway station"+","+to.toString();
                    else
                        destination=adapterView.getItemAtPosition(i).toString()+","+to.toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext()," Please Select Destination",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void openTACardView(View view)   {
      //  checknullfields();
        //if(flag==1) {
          //  Toast.makeText(this, "Fill Form Completely", Toast.LENGTH_SHORT).show();
            //flag=0;
        //}
        //else {

            Intent i = new Intent(this, Agent.class);

            i.putExtra("name",name.getText().toString());
            i.putExtra("address",address.getText().toString());
            i.putExtra("email",email.getText().toString());
            i.putExtra("phone",phone.getText().toString());
            i.putExtra("from",autoCompleteTextViewfrom.getText().toString());
            i.putExtra("to",destination);
            i.putExtra("date",datefield.getText().toString());
            i.putExtra("time",timefield.getText().toString());
            i.putExtra("vtype",vtype);
            i.putExtra("bookingpref",bookingpref);
            i.putExtra("vsize",vsize);
            i.putExtra("vtime",vtime);

            startActivity(i);

            name.setText("");
            address.setText("");
            email.setText("");
            phone.setText("");
            autoCompleteTextViewfrom.setText("");
            datefield.setText("");
            timefield.setText("");
          //  flag = 0;
       // }
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
                    vtime="Drop-Down";
                    Toast.makeText(this, "drop", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wait:
                if (checked) {
                    alert();
                    break;
                }
        }
    }
    public void dateenter(View view){
        InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        com.example.myapplication.DatePicker.flag=1;
        com.example.myapplication.DatePicker datePicker=new DatePicker();
        datePicker.show(getSupportFragmentManager(),"date");
    }
    public void timeset(View view){
        InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        TimePicker timePicker=new TimePicker();
        timePicker.show(getSupportFragmentManager(),"time");

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


   public void checknullfields(){
        if(TextUtils.isEmpty(autoCompleteTextViewfrom.getText().toString()))
            flag=1;
         else if(TextUtils.isEmpty(destination.toString()))
            flag=1;
         else if(TextUtils.isEmpty(name.getText().toString()))
            flag=1;
         else if(TextUtils.isEmpty(address.getText().toString()))
           flag=1;
         else if(TextUtils.isEmpty(email.getText().toString()))
            flag=1;
         else if(TextUtils.isEmpty(phone.getText().toString()))
            flag=1;
        else if(TextUtils.isEmpty(datefield.getText().toString()))
          flag=1;
        else if(TextUtils.isEmpty(timefield.getText().toString()))
          flag=1;
    }


}

