package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.fragment.WorkMateDetailFragment;
import com.ksntechnology.nursingschedule.fragment.WorkMateMainFragment;

public class FindWorkMateDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_work_mate_detail);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //
        }

        initInstance();
        if (savedInstanceState == null) {
            Intent itn = getIntent();
            String location = itn.getStringExtra("location");
            String shift = itn.getStringExtra("shift");
            String date = itn.getStringExtra("date");

            //Log.d("ResponsesXYZ", location + " " + date);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentDetail_FindWorkMate,
                            WorkMateDetailFragment.newInstance(
                                    location,
                                    date,
                                    shift
                            ))
                    .commit();
        }
    }

    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
