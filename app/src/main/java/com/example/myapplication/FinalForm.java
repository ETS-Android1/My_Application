package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FinalForm extends AppCompatActivity {

    TextView travelname,travellocation,travelcontact,travelemail,userfrom,userto,username,
            useraddress,usercontact,useremail,pref,drop,date,time,vtype,vsize,price;

   // static Firebase firebase;
    String uid;
    Button booknow;
    String bookingid;
    String currentdate;
    ImageView shop,pamphlet;
    String priceentered;
    String imgoneurl,imgtwourl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_form);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Agents");

        travelemail=(TextView)findViewById(R.id.travelemail);
        booknow=(Button)findViewById(R.id.booknow);
        travelname=(TextView)findViewById(R.id.travelagentname);
        travellocation=(TextView)findViewById(R.id.travellocation);
        travelcontact=(TextView)findViewById(R.id.travlecontact);
        username=(TextView)findViewById(R.id.username);
        useraddress=(TextView)findViewById(R.id.useraddress);
        useremail=(TextView)findViewById(R.id.useremail);
        usercontact=(TextView)findViewById(R.id.usercontact);
        userfrom=(TextView)findViewById(R.id.userfrom);
        userto=(TextView)findViewById(R.id.userto);
        pref=(TextView)findViewById(R.id.usrbookingpref);
        drop=(TextView)findViewById(R.id.uservtime);
        date=(TextView)findViewById(R.id.userdate);
        time=(TextView)findViewById(R.id.usertime);
        vtype=(TextView)findViewById(R.id.uservtype);
        vsize=(TextView)findViewById(R.id.uservsize);
        shop=(ImageView)findViewById(R.id.ta_shop);
        pamphlet=(ImageView)findViewById(R.id.pamphlet);
        price=(TextView)findViewById(R.id.price);

        Bundle bundle=getIntent().getExtras();
        travelemail.setText(bundle.getString("agentemail"));
        travelname.setText(bundle.getString("agentname"));
        travellocation.setText(bundle.getString("agentaddress"));
        travelcontact.setText(bundle.getString("agentcontact"));
        username.setText(bundle.getString("name"));
        useraddress.setText(bundle.getString("address"));
        useremail.setText(bundle.getString("email"));
        usercontact.setText(bundle.getString("phone"));
        userfrom.setText(bundle.getString("from"));
        userto.setText(bundle.getString("to"));
        pref.setText(bundle.getString("travelpref"));
        drop.setText(bundle.getString("cabtime"));
        date.setText(bundle.getString("date"));
        time.setText(bundle.getString("time"));
        vtype.setText(bundle.getString("cartype"));
        vsize.setText(bundle.getString("carsize"));

        imgoneurl=bundle.getString("imgone");
        imgtwourl=bundle.getString("imgtwo");

        priceentered=bundle.getString("price");

        if (priceentered.equals("null")) {
            price.setText("Not Available!");
        }
        else{
         price.setText("INR "+priceentered);
        }




        if(!(imgtwourl.equals("null"))){

            Toast.makeText(FinalForm.this,"Error..uploading images",Toast.LENGTH_SHORT).show();
        }


        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //HomePage.(view.getContext());
                if(HomePage.connected==true)
                showalertdialog();
            }
        });

    }

    public void showalertdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FinalForm.this);
        builder.setTitle("Booking");
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to book ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                final ProgressDialog pd = new ProgressDialog(FinalForm.this);
                pd.setMessage("Please Wait...");
                pd.setCancelable(false);
                pd.show();

                currentdate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                bookingid = uid.substring(2, 8) + String.valueOf(System.currentTimeMillis() / 10000);

                YourBookingPOJO yourBookingPOJO = new YourBookingPOJO(
                        travelemail.getText().toString(),
                        bookingid,
                        currentdate,
                        travelname.getText().toString(),
                        travellocation.getText().toString(),
                        travelcontact.getText().toString(),
                        username.getText().toString(),
                        useraddress.getText().toString(),
                        usercontact.getText().toString(),
                        useremail.getText().toString(),
                        userfrom.getText().toString(),
                        userto.getText().toString(),
                        date.getText().toString(),
                        time.getText().toString(),
                        pref.getText().toString(),
                        vtype.getText().toString(),
                        vsize.getText().toString(),
                        drop.getText().toString(),
                        uid.toString());
            }
        });
    }
}