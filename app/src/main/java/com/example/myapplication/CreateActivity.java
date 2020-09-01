package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class CreateActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    TabLayout tablayout;
    ViewPagerAdapter viewpageradapter;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

       firebaseAuth= FirebaseAuth.getInstance();

       if(firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(CreateActivity.this, "You are already signed in.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CreateActivity.this, HomePage.class));
       }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("User/Admin");
        setSupportActionBar(toolbar);

        tablayout=(TabLayout)findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.container);
        viewpageradapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpageradapter.addfragment(new CreateUser(),"User");
        viewpageradapter.addfragment(new CreateAgent(),"Agent");
        mViewPager.setAdapter(viewpageradapter);
        tablayout.setupWithViewPager(mViewPager);
    }

}