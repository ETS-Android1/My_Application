package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class ProfilePage extends AppCompatActivity {

    private static final int GalleryPick = 1;
    public String downloadImageUrl;
    public ProgressDialog loadingBar;
    private TextView mname, my_order, upload_design, profile_sett;
    private ImageView mimage;
    private Button logout;
    private ProgressBar progressBar;
    private Uri ImageUri;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;




    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        mname = findViewById(R.id.pofile_name);
        my_order = findViewById(R.id.profile_order);
        upload_design = findViewById(R.id.pofile_dupload);
        logout = findViewById(R.id.sign_out);
        profile_sett = findViewById(R.id.profile_setting);
        loadingBar = new ProgressDialog(getApplicationContext());
        //progressBar = view.findViewById(R.id.progress);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        mimage = findViewById(R.id.profile_image);

        userInfoDisplay(mname,mimage);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePage.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePage.this, ShowBooking.class);
                startActivity(intent);

            }
        });
        profile_sett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePage.this, Profile_Update.class);
                startActivity(intent);

            }
        });

    }

        private void userInfoDisplay(TextView mname, ImageView mimage) {

            FirebaseFirestore UsersRef = FirebaseFirestore.getInstance();
            firebaseAuth.getCurrentUser();
            UsersRef.collection("USERS/AGENTS/Bookng Information").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                public void onComplete(Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String name = document.get("username").toString();
                            Picasso.get().load("image").placeholder(R.drawable.profile).into(mimage);
                            mname.setText(name);
                        }
                    }
                }
            });

        }
    }