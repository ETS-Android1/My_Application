package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FinalForm extends AppCompatActivity {

    TextView ag_names,tag_names, ag_location, ag_contact, ag_email, userfrom, userto, username,
            useraddress, usercontact, useremail, pref, drop, date, time, vtype, vsize, price;

    // static Firebase firebase;
    String uid;
    Button booknow;
    String bookingid;
    String currentdate;
    ImageView shop;
    String priceentered;
    String imgoneurl, imgtwourl;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_form);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Confirm Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance(); //Authentication
        firebaseFirestore = FirebaseFirestore.getInstance();

        booknow = findViewById(R.id.book);
        ag_email = findViewById(R.id.travelemail);
        ag_names = findViewById(R.id.travelagentname);
        tag_names=findViewById(R.id.agentname);
        ag_location = findViewById(R.id.travellocation);
        ag_contact = findViewById(R.id.travlecontact);
        username = findViewById(R.id.username);
        useraddress = findViewById(R.id.useraddress);
        useremail = findViewById(R.id.useremail);
        usercontact = findViewById(R.id.usercontact);
        userfrom = findViewById(R.id.userfrom);
        userto = findViewById(R.id.userto);
        pref = findViewById(R.id.usrbookingpref);
        //  drop=(TextView)findViewById(R.id.uservtime);
        date = findViewById(R.id.userdate);
        time = findViewById(R.id.usertime);
        vtype = findViewById(R.id.uservtype);
        vsize = findViewById(R.id.uservsize);
        shop = findViewById(R.id.ta_shop);
        price = findViewById(R.id.price);

        // Data from Locale form
        username.setText(BookingForm.uname);
        useremail.setText(BookingForm.uemail);
        useraddress.setText(BookingForm.uaddress);
        usercontact.setText(BookingForm.uphone);
        userfrom.setText(BookingForm.from);
        userto.setText(BookingForm.destination);
        pref.setText(BookingForm.bookingtype);
        date.setText(BookingForm.datefield);
        time.setText(BookingForm.timefield);
        vtype.setText(BookingForm.vtype);
        vsize.setText(BookingForm.vsize);

        // Data from Agent card
        String ag_name = getIntent().getStringExtra("ag_name");
        String ag_address = getIntent().getStringExtra("ag_address");
        String ag_phone = getIntent().getStringExtra("ag_contact");


        //set data to views
        ag_names.setText(ag_name);
        ag_location.setText(ag_address);
        ag_contact.setText(ag_phone);

        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                book(view);
            }
        });

    }


    public void book(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FinalForm.this);
        builder.setTitle("Booking");
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to book ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                ProgressDialog pd = new ProgressDialog(FinalForm.this);
                pd.setMessage("Please Wait...");
                pd.setCancelable(false);
                pd.show();

                currentdate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
              //  bookingid = uid.substring(2, 8) + System.currentTimeMillis() / 10000;
                bookingid = String.valueOf((int) (Math.random() * Math.pow(8, 10)));


                Map<Object, String> userdata = new HashMap<>();
                userdata.put("bookingid", bookingid);
                // user info
                userdata.put("username", username.getText().toString());
                userdata.put("usermobile", usercontact.getText().toString());
                userdata.put("useremail", useremail.getText().toString());
                userdata.put("useradd", useraddress.getText().toString());
                // agency info
                userdata.put("tag_name", tag_names.getText().toString());
                userdata.put("ag_name", ag_names.getText().toString());
                userdata.put("ag_mobile", ag_contact.getText().toString());
                userdata.put("ag_email", ag_email.getText().toString());
                userdata.put("ag_add", ag_location.getText().toString());

                //booking info
                userdata.put("dep_add", userfrom.getText().toString());
                userdata.put("des_add", userto.getText().toString());
                userdata.put("booking_type", pref.getText().toString());
                userdata.put("booking_date", date.getText().toString());
                userdata.put("booking_time", time.getText().toString());
                userdata.put("vehi_type", vtype.getText().toString());
                userdata.put("vehi_size", vsize.getText().toString());


                firebaseFirestore.collection("Booking_Information").document(Objects.requireNonNull(firebaseAuth.getUid()))
                        .set(userdata)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent intent=new Intent(FinalForm.this,HomePage.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                ProgressDialog pd = new ProgressDialog(FinalForm.this);
                pd.setMessage("Cancelling...");
                pd.setCancelable(false);

            }
        });
        builder.create().show();

    }
}