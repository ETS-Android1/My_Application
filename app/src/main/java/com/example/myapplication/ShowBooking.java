package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ShowBooking extends AppCompatActivity {
    TextView nobooking;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showbookingcardlayout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Your Bookings");

        firebaseAuth = FirebaseAuth.getInstance(); //Authentication

        firebaseFirestore = FirebaseFirestore.getInstance();

        //RecyclerView
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        //set layout as LinearLayout
        recyclerView.setLayoutManager(layoutManager);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Booking_Information").document(firebaseAuth.getUid()).get();
    }


}
