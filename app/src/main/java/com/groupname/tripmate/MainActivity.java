package com.groupname.tripmate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //for navigation drawer at left top(a 3 lined button which opens navigation drawer)
    private DrawerLayout drawer;
    NavigationView navigationView;
    //Constants
    String TAG = "MyLOGS";
    String mUsername, mUserEmail;
    private static final int RC_SIGN_IN = 1;

    //firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;



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
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // user is signed in
                    Log.d(TAG, "onAuthStateChanged: Signed In");
                    Toast.makeText(MainActivity.this, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                    mUsername = user.getDisplayName();
                    mUserEmail = user.getEmail();
                } else {
                    // user is signed out
                    Toast.makeText(MainActivity.this, "Please signin", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onAuthStateChanged: User is signed out.");
                    //OnSignedOutInitialise();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    //.setLogo(R.drawable.ic_web_hi_res_512)
                                    //setting login screen theme
                                    .setTheme(R.style.SplashScreen)

                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };


    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    //return to main activity from google Auth with signin
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
                //after logout and login again home fragment should be selected in nav bar
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_fragment, new homeFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_home);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cannot work, until you sign in.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
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
                AuthUI.getInstance().signOut(getApplicationContext());// logout from app
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
