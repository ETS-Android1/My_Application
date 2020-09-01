package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Agent extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    private static ArrayList<Integer> removedItems;


    NavigationView navigationView;
    private ActionBarDrawerToggle Toggle;
    TextView navname;
    TextView navemail;
    int flag=0;
    TextView SignoutBtn;
    Boolean doublepress= false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);

      //  navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // View header=navigationView.getHeaderView(0);
        //navemail = (TextView)findViewById(R.id.accountemail);
       // navname = (TextView)findViewById(R.id.accountname);
      //SignoutBtn = (TextView) findViewById(R.id.nav_logout);





        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.versionArray[i],
                    MyData.id_[i]
                  // MyData.drawableArray[i]
            ));
        }

        removedItems = new ArrayList<Integer>();

        adapter = new CustomeAdapter(data);
        recyclerView.setAdapter(adapter);


    }

    private class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(this.context,FinalForm.class);
            startActivity(intent);        }


        }



}
/*
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
            Toast.makeText(Agent.this,"Press Again To Exit",Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doublepress=false;
                }
            },2000);
        }
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();

        if (id == R.id.registeruser) {

            // if(firebaseAuth.getCurrentUser()==null){
            //   Toast.makeText(HomePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            //}
            //// else {
            Intent intent=new Intent(this,ProfilePage.class);
            startActivity(intent);
            //  }

        } else if (id == R.id.registertravelagent) {

            ///  if(firebaseAuth.getCurrentUser()==null){
            //   Toast.makeText(HomePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            //}
            // else {
            Intent intent=new Intent(this,Agent.class);
            startActivity(intent);
            //}
        }
        else if(id==R.id.confirmation){
           /* if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(HomePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else {
            Intent intent = new Intent(this, BookingConfirmation.class);
            startActivity(intent);
            //}
        }
        else if(id==R.id.registration){
           if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(HomePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else {
            Intent intent = new Intent(this, CreateActivity.class);
            startActivity(intent);
            //}
        }
        else if(id == R.id.nav_logout){
            //  if(firebaseAuth.getCurrentUser()== null){

            navname.setText("Guest");
            navemail.setText("No Email,Register To Our App.");
            Toast.makeText(Agent.this, "You are logged out.", Toast.LENGTH_SHORT).show();
            //}
            // else
            Toast.makeText(Agent.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.linking) {
            startActivity(new Intent(this,LinkedCities.class));

        } else if (id == R.id.booking) {

            if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(HomePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else{
           // Intent intent=new Intent(this,ShowBooking.class);
         //   startActivity(intent);
            // }

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
            Toast.makeText(Agent.this, "Barcode will be given soon.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {

            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.putExtra(intent.EXTRA_EMAIL,new String[]{"sy4306122@gmail.com"});
            intent.putExtra(intent.EXTRA_SUBJECT,"Feedback By:"+navname.getText());
            intent.setType("message/rfc822");
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (Toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

   */
