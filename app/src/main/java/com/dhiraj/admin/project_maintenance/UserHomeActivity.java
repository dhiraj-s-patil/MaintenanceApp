package com.dhiraj.admin.project_maintenance;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dhiraj.admin.project_maintenance.Fragment.AssignedRequestsFragment;
import com.dhiraj.admin.project_maintenance.Fragment.CompletedRequestsFragment;
import com.dhiraj.admin.project_maintenance.Fragment.PendingRequestsFragment;
import com.dhiraj.admin.project_maintenance.Fragment.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;

public class UserHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //display pending requests page by default
        Fragment fragment = new PendingRequestsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.home_main_frame,fragment);
        ft.commit();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = new PendingRequestsFragment();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_pending_requests) {
            fragment = new PendingRequestsFragment();
        } else if (id == R.id.nav_assigned_requests) {
            fragment = new AssignedRequestsFragment();
        } else if (id == R.id.nav_completed_requests) {
            fragment = new CompletedRequestsFragment();
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this,RegistrationActivity.class));
        }

        if(fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.home_main_frame,fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
