package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfilePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerlayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle Toggle;
    TextView navname;
    TextView navemail;
    Toolbar toolbar;
    TextView navLogout;
    TextView email;
    Button SignoutBtn;
    TextView mname;
    TextView mmobile;
    TextView madd;
   FirebaseAuth firebaseAuth;
   FirebaseUser firebaseUser;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        navemail = (TextView)findViewById(R.id.accountemail);
        navname = (TextView)findViewById(R.id.accountname);
        navLogout = (TextView) findViewById(R.id.nav_logout);


        email = (TextView) findViewById(R.id.pofile_mail);
        mname = (TextView)findViewById(R.id.pofile_name);
        mmobile=(TextView)findViewById(R.id.pofile_mob) ;
        madd = (TextView)findViewById(R.id.pofile_add);
        SignoutBtn = (Button)findViewById(R.id.sign_out);

      firebaseAuth = FirebaseAuth.getInstance();
      firebaseUser=firebaseAuth.getCurrentUser();

        email.setText(firebaseUser.getEmail());
        //navemail.setText(firebaseUser.getEmail());



        SignoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FirebaseAuth.getInstance().signOut();
                Intent intent=(new Intent(ProfilePage.this,MainActivity.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent );
            }
        });


        mDrawerlayout=(DrawerLayout)findViewById(R.id.drawer);
        Toggle=new ActionBarDrawerToggle(this , mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(Toggle);
        Toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

            if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(ProfilePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent=new Intent(this,ProfilePage.class);
                startActivity(intent);
           }

        } else if (id == R.id.registertravelagent) {

            if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(ProfilePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
           }
        }
        else if(id==R.id.confirmation){
            if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(ProfilePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(this, BookingConfirmation.class);
                startActivity(intent);
            }
        }
        else if(id==R.id.registration){
            if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(ProfilePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(this, RegisterTA.class);
                startActivity(intent);
            }
        }
        else if(id == R.id.nav_logout){
           if(firebaseAuth.getCurrentUser()== null){
                navname.setText("Guest");
                navemail.setText("No Email,Register To Our App.");
                toolbar.setTitle("Guest");
                Toast.makeText(ProfilePage.this, "You are logged out.", Toast.LENGTH_SHORT).show();
           }
            else
                FirebaseAuth.getInstance().signOut();
                Intent intent=(new Intent(ProfilePage.this,MainActivity.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent );        }
        else if (id == R.id.linking)
        {
            startActivity(new Intent(this,LinkedCities.class));

        } else if (id == R.id.booking) {

            if(firebaseAuth.getCurrentUser()==null){
                Toast.makeText(ProfilePage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ProfilePage.this, "Barcode will be given soon.", Toast.LENGTH_SHORT).show();

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
