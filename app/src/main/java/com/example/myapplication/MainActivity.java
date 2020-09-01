package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView donthaveAnAccount;
    private TextView forgotpassword;
    private EditText email;
    private EditText password;
    private Button SignInBtn;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        donthaveAnAccount = (TextView) findViewById(R.id.tv_donthaveacc);

        donthaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivity = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intentActivity);
                finish();
            }
        });

        forgotpassword =(TextView)findViewById(R.id.sign_in_forgot);

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivity = new Intent(MainActivity.this, ResetPassword.class);
                startActivity(intentActivity);
                finish();
            }
        });

        email = (EditText)findViewById(R.id.sign_in_email);
        password = (EditText)findViewById(R.id.sign_in_password);
        SignInBtn = (Button)findViewById(R.id.sign_in_btn);
        progressBar = (ProgressBar)findViewById(R.id.sign_in_progressbar);
        firebaseAuth = FirebaseAuth.getInstance();

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });

    }

    private void CheckInputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                SignInBtn.setEnabled(true);
            }else{
                SignInBtn.setEnabled(false);
            }
        }else {
            SignInBtn.setEnabled(false);
        }
    }

    private void checkEmailAndPassword(){
        if(email.getText().toString().matches(emailPattern)){
            if(password.length()>=8){

                progressBar.setVisibility(View.VISIBLE);
                SignInBtn.setEnabled(false);

               firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent mainIntent = new Intent(MainActivity.this,HomePage.class);
                                    startActivity(mainIntent);
                                    finish();
                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    SignInBtn.setEnabled(true);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(MainActivity.this,error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                Intent mainIntent = new Intent(MainActivity.this,HomePage.class);
                startActivity(mainIntent);
                finish();
            }else {
                Toast.makeText(MainActivity.this,"Incorrect email and password",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(MainActivity.this,"Incorrect email and password",Toast.LENGTH_SHORT).show();
        }
    }
}
