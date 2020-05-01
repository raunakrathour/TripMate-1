package com.groupname.tripmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import com.firebase.ui.auth.AuthUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //for navigation drawer at left top(a 3 lined button which opens navigation drawer)
    private DrawerLayout drawer;
    NavigationView navigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //for rotating toogle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //if app opened home fragment is selected
         if(savedInstanceState==null) {
             getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, new homeFragment()).commit();
             navigationView.setCheckedItem(R.id.nav_home);
         }


         //firebase signin



    }


//when nav is open and we press back then it will close nav drawer
    //else close app
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    //navigation drawer items onselect will refer to following places
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_home://select home button
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment,new homeFragment()).commit();//goto home fragment
                break;
            case R.id.nav_logout://select logout button
                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        Toast.makeText(MainActivity.this,"Hope you will be back soon",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this,login.class));
                        MainActivity.this.finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                       Toast.makeText(MainActivity.this,"Error "+fault.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });// logout from app


                break;
            case R.id.nav_drivers://select driver button
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment,new Drivers_Fragment()).commit();//open drivers fragment
                break;
            case R.id.nav_view_announcement://select make announcement
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment,new ViewAnnouncement_Fragment()).commit();//open new announceent fragment
                break;
            case R.id.nav_myaccount://select My Account
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment,new MyAccount_fragment()).commit();//open My Account fragment
                break;
            case R.id.nav_set_alarm://select setAlarm
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment,new SetAlarm_Fragment()).commit();//open SetAlarm fragment
                break;
            case R.id.nav_share://select share
                Toast.makeText(this, "Share your App", Toast.LENGTH_SHORT).show();//share app
                break;
            case R.id.nav_feedback://select feed back
                Toast.makeText(this, "Give Your Valuable Feedback", Toast.LENGTH_SHORT).show();//send feedback, it opens mail send it to Tripcare@gmail.com
                break;
        }
        //when an item in the nav drawer is selected it will close the drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
