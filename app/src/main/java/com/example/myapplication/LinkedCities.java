package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LinkedCities extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    private DrawerLayout mDrawerlayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle Toggle;

    TextView SignoutBtn;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Boolean doublepress= false;
    static Boolean connected=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_cities);

        mDrawerlayout = findViewById(R.id.drawer);
        Toggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(Toggle);
        Toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("HomePage");

        listView = findViewById(R.id.citylistview);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.citylistlayout, R.id.citytext, getResources().getStringArray(R.array.cityarray));
        listView.setAdapter(arrayAdapter);

    }
    @Override
    public void onBackPressed() {
        mDrawerlayout = findViewById(R.id.drawer);
        if (mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            if(doublepress){
                super.onBackPressed();
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        if (id == R.id.registeruser) {
            if(firebaseAuth.getCurrentUser()==null)
            {
                Toast.makeText(LinkedCities.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent intent=new Intent(this,ProfilePage.class);
                startActivity(intent);
            }
        } else if (id == R.id.registertravelagent)
        {
            if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(LinkedCities.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent=new Intent(this,Agent.class);
                startActivity(intent);
            }
        }
        else if(id==R.id.confirmation){
            if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(LinkedCities.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(this, BookingConfirmation.class);
                startActivity(intent);
            }
        }
        else if(id==R.id.registration){
            if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(LinkedCities.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(this, CreateActivity.class);
                startActivity(intent);
            }
        }
        else if(id == R.id.nav_logout){
            if(firebaseAuth.getCurrentUser()== null){

               // navname.setText("Guest");
                //navemail.setText("No Email,Register To Our App.");
                Toast.makeText(LinkedCities.this, "You are logged out.", Toast.LENGTH_SHORT).show();
            }
            else
                FirebaseAuth.getInstance().signOut();
            Intent intent=(new Intent(LinkedCities.this,MainActivity.class));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent );
        }
        else if (id == R.id.linking) {
            startActivity(new Intent(this,LinkedCities.class));

        } else if (id == R.id.booking) {
            if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(LinkedCities.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent=new Intent(this,ShowBooking.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.contact) {

            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            final String[] phone={"8874423638","9779552562","9415190391"};
            alert.setTitle("Helpline Numbers:");
            alert.setItems(phone, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone[i]));
                    startActivity(intent);
                }
            });
            alert.create().show();
        }
        else if (id == R.id.nav_share) {
            Toast.makeText(LinkedCities.this, "Barcode will be given soon.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sy4306122@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback By:");
            intent.setType("message/rfc822");
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}

