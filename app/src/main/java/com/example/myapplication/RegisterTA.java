package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterTA extends AppCompatActivity {

    EditText name, location, phone, email, price;
    Button register;
    ImageView imgone, imgtwo, cancelone, canceltwo;
    int flag = 0;

    private int IMGONE = 1;
    private int IMGTWO = 2;

    String imgonedwnld;
    String imgtwodwnld = "null";
    Uri imgurione = null, imguritwo = null;
    ProgressDialog progressDialog;
    //   private FirebaseAuth firebaseAuth;
    //   private FirebaseFirestore firebaseFirestore;
    //  private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ta);

        imgtwodwnld = "null";
        imgurione = null;
        imguritwo = null;
        name = (EditText) findViewById(R.id.name);
        location = (EditText) findViewById(R.id.location);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.mobile);
        register = (Button) findViewById(R.id.register);
        price = (EditText) findViewById(R.id.price);


        imgone = (ImageView) findViewById(R.id.imgone);
        imgtwo = (ImageView) findViewById(R.id.imgtwo);
        cancelone = (ImageView) findViewById(R.id.cancelone);
        canceltwo = (ImageView) findViewById(R.id.canceltwo);

        cancelone.setClickable(false);
        canceltwo.setClickable(false);


        imgone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select Picture"),IMGONE);
            }
        });

        imgtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select Picture"),IMGTWO);
            }
        });

        cancelone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(view.getContext());
                progressDialog.setMessage("Wait..");
                progressDialog.show();

            }
        });

        canceltwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(view.getContext());
                progressDialog.setMessage("Wait..");
                progressDialog.show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgurione != null) {
                    flag = 0;
                    checknullfields();

                    if (flag == 0)
                    {
                            showalertbox();

                    } else {
                        Toast.makeText(RegisterTA.this, "Fill Completely..", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterTA.this, "Upload images..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void showalertbox(){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Register");
        alert.setMessage("Are you sure you want to register your travel agency?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final ProgressDialog pd=new ProgressDialog(RegisterTA.this);
                pd.setMessage("Wait..");
                pd.show();

             /*  TA_POJO ta=new TA_POJO(name.getText().toString(),
                        location.getText().toString(),phone.getText().toString(),email.getText().toString(),price.getText().toString(),
                        imgonedwnld,imgtwodwnld);
        */


            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.create().show();
    }

    public void checknullfields(){
        flag=0;
        if(TextUtils.isEmpty(name.getText()))
            flag=1;
        else if(TextUtils.isEmpty(location.getText()))
            flag=1;
        else if(TextUtils.isEmpty(phone.getText()))
            flag=1;
        else if(TextUtils.isEmpty(email.getText()))
            flag=1;
        else if(TextUtils.isEmpty(price.getText()))
            price.setText("null");
        else if(imguritwo==null)
            imgtwodwnld="null";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMGONE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Wait..");
            progressDialog.show();
            uploadimage(data.getData(),1);
        }
        if(requestCode==IMGTWO && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Wait..");
            progressDialog.show();
            uploadimage(data.getData(),2);
        }
    }

    public void uploadimage(final Uri uri, final int number){
        ContentResolver cr = getBaseContext().getContentResolver();
        try {
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            final byte[] data = baos.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
