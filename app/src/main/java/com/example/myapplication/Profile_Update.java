package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.apnadarzi.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

//import de.hdodenhof.circleimageview.CircleImageView;


public class Profile_Update extends AppCompatActivity {
    private static final int GalleryPick = 1;
    private ImageView profileImageView;
    private EditText fullNameEditText, userPhoneEditText, lastNameEditText, emailEditText, stateEditText;
    private TextView profileChangeTextBtn, closeTextBtn, saveTextButton;
    private Uri imageUri;
    private String myUrl = "", document;
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        profileImageView = findViewById(R.id.settings_profile_image);
        fullNameEditText = findViewById(R.id.settings_full_name);
        userPhoneEditText = findViewById(R.id.settings_mobile);
        stateEditText = findViewById(R.id.settings_Address);
        emailEditText = findViewById(R.id.settings_email);
        profileChangeTextBtn = findViewById(R.id.profile_image_change_btn);
        closeTextBtn = findViewById(R.id.close_settings_btn);
        saveTextButton = findViewById(R.id.update_account_settings_btn);

        firebaseAuth = FirebaseAuth.getInstance(); //Authentication
        firebaseUser=firebaseAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();
        userInfoDisplay(profileImageView, fullNameEditText, userPhoneEditText, emailEditText, stateEditText);

        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.equals("clicked")) {
                    userInfoSaved();
                } else {
                    updateOnlyUserInfo();
                }
            }
        });
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";
                OpenGallery();
            }
        });

        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";
                OpenGallery();
                //  CropImage.activity(imageUri).setAspectRatio(1, 1).start(Profile_Update.this);
            }
        });


    }

    private void updateOnlyUserInfo() {
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("username", fullNameEditText.getText().toString());
        userMap.put("useremail", emailEditText.getText().toString());
        userMap.put("usermobile", userPhoneEditText.getText().toString());
        userMap.put("useradd", stateEditText.getText().toString());

        firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).set(userMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Profile_Update.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
            }
        });

        startActivity(new Intent(Profile_Update.this, ProfilePage.class));
        Toast.makeText(Profile_Update.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);
        } else {

            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(Profile_Update.this, Profile_Update.class));
            finish();
        }
    }


    private void userInfoSaved() {
        if (TextUtils.isEmpty(fullNameEditText.getText().toString())) {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPhoneEditText.getText().toString())) {
            Toast.makeText(this, "Phone is mandatory.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(emailEditText.getText().toString())) {
            Toast.makeText(this, "Email is mandatory.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(stateEditText.getText().toString())) {
            Toast.makeText(this, "Address is mandatory.", Toast.LENGTH_SHORT).show();
        } else if (checker.equals("clicked")) {
            uploadImage();
        }
    }
    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (imageUri != null) {
            final StorageReference fileRef = storageProfilePrictureRef
                    .child(firebaseAuth.getUid()+ ".jpg");

            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();
                        CollectionReference ref = FirebaseFirestore.getInstance().collection("USERS");
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("username", fullNameEditText.getText().toString());
                        userMap.put("useradd", stateEditText.getText().toString());
                        userMap.put("useremail", emailEditText.getText().toString());
                        userMap.put("usermobile", userPhoneEditText.getText().toString());
                        userMap.put("userimage", myUrl);
                        ref.document(firebaseAuth.getUid()).set(userMap);
                        progressDialog.dismiss();
                        startActivity(new Intent(Profile_Update.this, ProfilePage.class));
                        Toast.makeText(Profile_Update.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Profile_Update.this, "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }

    }

    private void userInfoDisplay(final ImageView profileImageView, final EditText fullNameEditText, final EditText userPhoneEditText, final EditText addressEditText, final  EditText emailEditText) {

        FirebaseFirestore UsersRef = FirebaseFirestore.getInstance();
        UsersRef.collection("USERS").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                         //String image = document.get("").toString();
                        String firstname = document.get("username").toString();
                        String mobile = document.get("usermobile").toString();
                        String email = document.get("useremail").toString();
                        String add = document.get("useradd").toString();
                        Picasso.get().load("userimage").placeholder(R.drawable.profile).into(profileImageView);
                        fullNameEditText.setText(firstname);
                        userPhoneEditText.setText(mobile);
                        addressEditText.setText(add);
                        emailEditText.setText(email);

                    }
                }
            }
        });
    }
}
