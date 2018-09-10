package com.necohorne.socialmanager.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.necohorne.socialmanager.Models.InstagramUser;
import com.necohorne.socialmanager.R;
import com.necohorne.socialmanager.Utilities.SocialUtils.DiscordUtils;
import com.necohorne.socialmanager.Utilities.SocialUtils.InstagramUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Intent mLogOutIntent;
    private boolean isActivityRunning;
    private InstagramUser mInstagramUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLogOutIntent = new Intent( MainActivity.this, LoginActivity.class );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new MainActivity.FetchInstagramUser().execute();
    }

    @Override
    protected void onStart() {
        checkAuthenticationState();
        isActivityRunning = true;
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id){
            case R.id.nav_social:
                startActivity(new Intent(MainActivity.this , SocialSelectionActivity.class));
                break;
            case R.id.nav_gallery:
                //
                break;
            case R.id.nav_slideshow:
                //
                break;
            case R.id.nav_manage:
                //
                break;
            case R.id.nav_share:
                //
                break;
            case R.id.nav_send:
                //
                break;
            case R.id.nav_log_out:
                logOut();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Firebase Methods
    private void checkAuthenticationState() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            mLogOutIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity( mLogOutIntent );
            finish();
        }
    }

    private void logOut() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText( MainActivity.this, "Successfully logged out", Toast.LENGTH_LONG ).show();
            startActivity( mLogOutIntent );
            finish();
        }
    }

    public class FetchInstagramUser extends AsyncTask<Void, Void, InstagramUser> {

        @Override
        protected InstagramUser doInBackground(Void... voids) {
            return InstagramUtils.getInstagramUser(MainActivity.this);
        }

        @Override
        protected void onPostExecute(InstagramUser instagramUser) {
            mInstagramUser = instagramUser;
            super.onPostExecute(instagramUser);
        }
    }
}
