package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ksntechnology.nursingschedule.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView tvUserName;
    private TextView tvEmail;
    private TextView tvTopic;
    private LinearLayout btnViewData;
    private LinearLayout btnAddItem;
    private LinearLayout btnViewTeam;
    private LinearLayout btnViewStatistic;
    private String mUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //
        }

        initInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(
                        MainActivity.this,
                        SettingActivity.class
                ));
                overridePendingTransition(
                        R.anim.from_bottom,
                        R.anim.to_top
                );
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /*************************************
     *  Custom Method
     */
    private void toastMessage(String text) {
        Toast.makeText(this,
                text,
                Toast.LENGTH_SHORT).show();
    }


    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(fabClickListener);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        tvUserName = headerView.findViewById(R.id.text_userName);
        tvEmail = headerView.findViewById(R.id.text_email);
        tvTopic = findViewById(R.id.text_topic);

        btnViewData = findViewById(R.id.button_viewData);
        btnAddItem = findViewById(R.id.button_AddItem);
        btnViewTeam = findViewById(R.id.button_viewFriends);
        btnViewStatistic = findViewById(R.id.button_viewStatistic);
        
        btnViewData.setOnClickListener(btnViewDataClickListener);
        btnAddItem.setOnClickListener(btnAddItemClickListener);
        btnViewTeam.setOnClickListener(btnViewTeamClickListener);
        btnViewStatistic.setOnClickListener(btnViewStatisticClickListener);

        initUserLogin();
    }


    private void initUserLogin() {
        Intent intent = getIntent();
        String userName = intent.getStringExtra("user_name");
        String email = intent.getStringExtra("email");

        mUser = userName;
        tvUserName.setText("Hi " + userName);
        tvEmail.setText(email);
        //tvTopic.setText(userName + " : " + email);

        //toastMessage(userName + " : " + email);
    }



    /*************************************
     *  Listener Zone
     */
    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action",
                    Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    };

    View.OnClickListener btnViewDataClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //
        }
    };

    View.OnClickListener btnAddItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddItemActivity.class
            );
            intent.putExtra("user_working", mUser);
            startActivity(intent);

            overridePendingTransition(
                    R.anim.from_bottom, R.anim.to_top
            );
        }
    };

    View.OnClickListener btnViewTeamClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //
        }
    };

    View.OnClickListener btnViewStatisticClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };



}
