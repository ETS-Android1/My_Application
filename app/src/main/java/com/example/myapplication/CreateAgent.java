package com.example.myapplication;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAgent extends Fragment {

    private TextView alreadyhaveAnAccount;

    private EditText memail;
    private EditText mname;
    private EditText agname;
    private EditText mmobile;
    private EditText madd;

    private EditText mpassword;
    private EditText mconfirmPassword;
    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    private ImageButton closeBtn;
    private Button SignUpBtn;
    private RadioButton gcbtn;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    String agencyty = "Good Carrier";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.create_agent, container, false);


        alreadyhaveAnAccount = view.findViewById(R.id.tv_alreadyhave);

        alreadyhaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        mname = view.findViewById(R.id.sign_up_name);
        agname = view.findViewById(R.id.sign_up_ln);
        mmobile = view.findViewById(R.id.sign_up_mobile);
        mpassword = view.findViewById(R.id.sign_up_password);
        memail = view.findViewById(R.id.sign_up_email);
        madd = view.findViewById(R.id.sign_up_add);
        mconfirmPassword = view.findViewById(R.id.sign_up_cp);
        gcbtn = view.findViewById(R.id.femalebtn);


        SignUpBtn = view.findViewById(R.id.sign_up_btn);

        progressBar = view.findViewById(R.id.sign_up_progressbar);

        firebaseAuth = FirebaseAuth.getInstance();
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

    private void CheckInputs() {
        if (!TextUtils.isEmpty(memail.getText())) {
            if (!TextUtils.isEmpty((mname.getText()))) {
                if (!TextUtils.isEmpty(mpassword.getText()) && mpassword.length() >= 8) {
                    SignUpBtn.setEnabled(!TextUtils.isEmpty(mconfirmPassword.getText()));
                } else {
                    SignUpBtn.setEnabled(false);
                }
            } else {
                SignUpBtn.setEnabled(false);
            }
        } else {
            SignUpBtn.setEnabled(false);
        }
    }

    private void CheckEmailAndPassword() {
        if (memail.getText().toString().matches(emailPattern)) {
            if (mpassword.getText().toString().equals(mconfirmPassword.getText().toString())) {

                progressBar.setVisibility(View.VISIBLE);
                SignUpBtn.setEnabled(false);

                firebaseAuth.createUserWithEmailAndPassword(memail.getText().toString(),mpassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Map<Object, String> userdata = new HashMap<>();
                                    userdata.put("name", mname.getText().toString());
                                    userdata.put("agency_name", agname.getText().toString());
                                    userdata.put("ag_mobile", mmobile.getText().toString());
                                    userdata.put("agency_add", madd.getText().toString());
                                    userdata.put("ag_email", memail.getText().toString());


                                    userdata.put("agencytype", agencyty);

                                    firebaseFirestore.collection("Transporter").add(userdata)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    startActivity(new Intent(getContext(), HomePage.class));
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
            } else {
                mconfirmPassword.setError("Password doesn't Match!!");
            }
        } else {
            memail.setError("Invalid email!!");
        }
    }
}