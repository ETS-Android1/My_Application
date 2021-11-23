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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateUser extends Fragment {

    private TextView alreadyhaveAnAccount;

    private EditText memail;
    private EditText mname;
    private EditText mmobile;
    private EditText madd;

    private EditText mpassword;
    private EditText mconfirmPassword;
    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    private ImageButton closeBtn;
    private Button SignUpBtn;
    private RadioButton female;

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    String gender;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.fragment_createuser, container, false);


        alreadyhaveAnAccount = view.findViewById(R.id.tv_alreadyhave);

        alreadyhaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        mname = view.findViewById(R.id.sign_up_name);
        mmobile = view.findViewById(R.id.sign_up_mobile);
        mpassword = view.findViewById(R.id.sign_up_password);
        memail = view.findViewById(R.id.sign_up_email);
        madd = (EditText) view.findViewById(R.id.sign_up_add);
        mconfirmPassword = view.findViewById(R.id.sign_up_cp);
        SignUpBtn = view.findViewById(R.id.sign_up_btn);

        progressBar = view.findViewById(R.id.sign_up_progressbar);

        firebaseAuth = FirebaseAuth.getInstance(); //Authentication
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

                firebaseAuth.createUserWithEmailAndPassword(memail.getText().toString(), mpassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Map<Object, String> userdata = new HashMap<>();
                                    userdata.put("username", mname.getText().toString());
                                    userdata.put("usermobile", mmobile.getText().toString());
                                    userdata.put("useremail", memail.getText().toString());
                                    userdata.put("useradd", madd.getText().toString());


                                   /* if (female.isChecked()) {
                                        gender = "Female";
                                    } else {
                                        gender = "Male";
                                    }
                                    userdata.put("gender", gender);
                                    */
                                   firebaseFirestore.collection("USERS").document(Objects.requireNonNull(firebaseAuth.getUid())).set(userdata)

                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    startActivity(new Intent(getContext(), HomePage.class));

                                                }
                                            });
                                } else {
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
