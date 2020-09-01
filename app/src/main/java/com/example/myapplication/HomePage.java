package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<com.example.myapplication.ModeTransport> list=new ArrayList<com.example.myapplication.ModeTransport>();
    TextView navname;
    TextView navemail;
    int flag=0;
    AutoCompleteTextView autoCompleteTextView;
    private DrawerLayout mDrawerlayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle Toggle;
    int [] image={R.drawable.newrailway,R.drawable.newairway,R.drawable.newlocal};
    String [] name={"Railways","Airport","Locale"};
    TextView SignoutBtn;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Boolean doublepress= false;
    static Boolean connected=false;
    //static FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



        navemail = (TextView)findViewById(R.id.accountemail);
        navname = (TextView)findViewById(R.id.accountname);
        SignoutBtn = (TextView) findViewById(R.id.nav_logout);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        mDrawerlayout=(DrawerLayout)findViewById(R.id.drawer);
        Toggle=new ActionBarDrawerToggle(this , mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(Toggle);
        Toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("HomePage");


        View header=navigationView.getHeaderView(0);



        Calendar c=Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);


        int count=0;

        for(String n:name){
            ModeTransport modeTransport=new ModeTransport(image[count],n);
            list.add(modeTransport);
            count++;
        }
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter=new ModeAdapter(list,this);
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void onBackPressed() {
        mDrawerlayout=(DrawerLayout)findViewById(R.id.drawer);
        if (mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            if(doublepress){
                super.onBackPressed();
                return;
            }
            doublepress=true;
            Toast.makeText(HomePage.this,"Press Again To Exit",Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doublepress=false;
                }
            },2000);
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
               Toast.makeText(HomePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else
                {
                Intent intent=new Intent(this,ProfilePage.class);
                startActivity(intent);
                }
        } else if (id == R.id.registertravelagent)
        {
            if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(HomePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent=new Intent(this,Agent.class);
                startActivity(intent);
            }
        }
        else if(id==R.id.confirmation){
            if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(HomePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(this, BookingConfirmation.class);
                startActivity(intent);
            }
        }
        else if(id==R.id.registration){
           if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(HomePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(this, CreateActivity.class);
                startActivity(intent);
            }
        }
        else if(id == R.id.nav_logout){
            if(firebaseAuth.getCurrentUser()== null){

                navname.setText("Guest");
                navemail.setText("No Email,Register To Our App.");
                Toast.makeText(HomePage.this, "You are logged out.", Toast.LENGTH_SHORT).show();
            }
            else
                FirebaseAuth.getInstance().signOut();
            Intent intent=(new Intent(HomePage.this,MainActivity.class));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent );
        }
        else if (id == R.id.linking) {
            startActivity(new Intent(this,LinkedCities.class));

        } else if (id == R.id.booking) {
           if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(HomePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
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
                    Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone[i].toString()));
                    startActivity(intent);
                }
            });
            alert.create().show();
        }
        else if (id == R.id.nav_share) {
            Toast.makeText(HomePage.this, "Barcode will be given soon.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {

            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.putExtra(intent.EXTRA_EMAIL,new String[]{"sy4306122@gmail.com"});
            intent.putExtra(intent.EXTRA_SUBJECT,"Feedback By:");
            intent.setType("message/rfc822");
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
