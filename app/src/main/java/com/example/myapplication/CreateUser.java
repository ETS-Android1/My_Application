package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateUser extends Fragment {

    private TextView alreadyhaveAnAccount;

    private EditText memail;
    private EditText mname;
    private EditText mlname;
    private EditText mmobile;
    private EditText madd;

    private EditText mpassword;
    private EditText mconfirmPassword;
    String gender = "";


    private ImageButton closeBtn;
    private Button SignUpBtn;
    private RadioButton male;
    private RadioButton female;

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.fragment_createuser, container, false);


        alreadyhaveAnAccount = (TextView) view.findViewById(R.id.tv_alreadyhave);

        alreadyhaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        mname = (EditText) view.findViewById(R.id.sign_up_name);
        mlname = (EditText) view.findViewById(R.id.sign_up_ln);
        mmobile = (EditText) view.findViewById(R.id.sign_up_mobile);
        mpassword = (EditText) view.findViewById(R.id.sign_up_password);
        memail = (EditText) view.findViewById(R.id.sign_up_email);
        madd = (EditText) view.findViewById(R.id.sign_up_name);
        mconfirmPassword = (EditText) view.findViewById(R.id.sign_up_cp);
        male = (RadioButton) view.findViewById(R.id.malebtn);
        female = (RadioButton) view.findViewById(R.id.femalebtn);


        SignUpBtn = (Button) view.findViewById(R.id.sign_up_btn);

        progressBar = (ProgressBar) view.findViewById(R.id.sign_up_progressbar);

        firebaseAuth = firebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        memail.addTextChangedListener(new TextWatcher() {
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
        mname.addTextChangedListener(new TextWatcher() {
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
        mpassword.addTextChangedListener(new TextWatcher() {
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
        mconfirmPassword.addTextChangedListener(new TextWatcher() {
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

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEmailAndPassword();
            }
        });
        return view;
    }
    private void CheckInputs()
    {
        if(!TextUtils.isEmpty(memail.getText())){
            if(!TextUtils.isEmpty((mname.getText()))){
                if(!TextUtils.isEmpty(mpassword.getText()) && mpassword.length()>=8){
                    if(!TextUtils.isEmpty(mconfirmPassword.getText())){
                        SignUpBtn.setEnabled(true);
                    }else{
                        SignUpBtn.setEnabled(false);
                    }
                }else {
                    SignUpBtn.setEnabled(false);
                }
            } else {
                SignUpBtn.setEnabled(false);
            }
        }else {
            SignUpBtn.setEnabled(false);
        }
    }

    private void CheckEmailAndPassword(){
        if(memail.getText().toString().matches(emailPattern)){
            if(mpassword.getText().toString().equals(mconfirmPassword.getText().toString())){

                progressBar.setVisibility(View.VISIBLE);
                SignUpBtn.setEnabled(false);

                firebaseAuth.createUserWithEmailAndPassword(memail.getText().toString(),mpassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    Map<Object,String > userdata = new HashMap<>();
                                    userdata.put("firstname",mname.getText().toString());
                                    userdata.put("lastname",mlname.getText().toString());
                                    userdata.put("mobile",mmobile.getText().toString());
                                    userdata.put("add",madd.getText().toString());


                                    if(female.isChecked()){
                                        gender="Female";
                                    }
                                    if(male.isChecked()){
                                        gender="Male";
                                    }

                                    firebaseFirestore.collection("USERS")
                                            .add(userdata)

                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                                }
                                            });


                                    startActivity(new Intent(getContext(),HomePage.class));
                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    SignUpBtn.setEnabled(true);
                                    Toast.makeText(getContext(), "Could not registered.Please try again..", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }else {
                mconfirmPassword.setError("Password doesn't Match!!");
            }
        }else {
            memail.setError("Invalid email!!");
        }
    }
}

