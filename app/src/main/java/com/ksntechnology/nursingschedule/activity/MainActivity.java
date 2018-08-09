package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuInflater;
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
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MenuItem.OnMenuItemClickListener,
        OnMenuItemLongClickListener, OnMenuItemClickListener {

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

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            /*case R.id.action_settings:
                startActivity(new Intent(
                        MainActivity.this,
                        SettingActivity.class
                ));
                overridePendingTransition(
                        R.anim.from_bottom,
                        R.anim.to_top
                );
                break;*/
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "Clicked on positionXXX: "
                + item.getItemId(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onMenuItemLongClick(View view, int i) {
        Toast.makeText(this, "Long clicked on position: "
                + i, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMenuItemClick(View view, int position) {
        /*Toast.makeText(this, "Clicked on position: "
                + position, Toast.LENGTH_SHORT).show();*/
        switch (position) {
            case 1:
                startActivity(new Intent(
                        MainActivity.this,
                        SettingActivity.class
                ));
                overridePendingTransition(
                        R.anim.from_bottom,
                        R.anim.to_top
                );
                break;
        }
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
    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject setting = new MenuObject("Setting");
        setting.setResource(R.drawable.ic_setting_icon_white);

        MenuObject send = new MenuObject("Send message");
        send.setResource(R.drawable.icn_1);

        menuObjects.add(close);
        menuObjects.add(setting);
        menuObjects.add(send);
        /*menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        menuObjects.add(block);*/

        return menuObjects;
    }

    protected void addFragment(Fragment fragment,
                               boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);

        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }


    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
        fragmentManager = getSupportFragmentManager();
        initMenuFragment();
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
            Intent intent = new Intent(
                    MainActivity.this,
                    ViewScheduleRecordActivity.class
            );
            intent.putExtra("user_working", mUser);
            startActivity(intent);

            overridePendingTransition(
                    R.anim.from_bottom, R.anim.to_top
            );
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
