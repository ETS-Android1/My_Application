package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Contact_Page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerlayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle Toggle;
    // Declare Variables
    GridView grid;
    GridAdapter adapter;
    String[] Version;
    int[] image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_page);

        mDrawerlayout = findViewById(R.id.drawer);
        Toggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(Toggle);
        Toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerlayout = findViewById(R.id.drawer);


        Version = new String[]{"Yadav Transport Company", "JBM Roadways", "Banda Shukla Transport",
                "Maurya Suppliers", "Baba Transport Service", "R.K. Grit Udyog",
                "Lakshmi Tour & Travels"};
        image = new int[] { R.drawable.ytc,R.drawable.jbmroad, R.drawable.bandashukla,
                R.drawable.maurya, R.drawable.babatran, R.drawable.rk,
                R.drawable.lakshmi};

        // Locate the ListView in listview_main.xml
        grid = findViewById(R.id.gridview);

        // Pass results to ListViewAdapter Class
        adapter = new GridAdapter(getApplicationContext(), Version,image);
        // Binds the Adapter to the ListView
        grid.setAdapter(adapter);
        // Capture ListView item click
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(getApplicationContext(),"You have selected :"+Version[position], Toast.LENGTH_SHORT).show();


            }

        });
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(Toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        switch (id) {

            case R.id.home:
                Intent h = new Intent(Contact_Page.this, HomePage.class);
                startActivity(h);
                break;
            case R.id.registeruser:
                Intent p = new Intent(Contact_Page.this, ProfilePage.class);
                startActivity(p);
                break;

            case R.id.booking:

                break;
            case R.id.contact:
                Intent i = new Intent(Contact_Page.this, Contact_Page.class);
                startActivity(i);
                break;


        }
        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}