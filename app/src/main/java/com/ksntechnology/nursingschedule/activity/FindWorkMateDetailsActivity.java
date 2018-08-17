package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.fragment.WorkMateDetailFragment;

public class FindWorkMateDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_work_mate_details);
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
                    .add(R.id.contentContainer_FindWorkMateDetail,
                            WorkMateDetailFragment.newInstance(
                                    location,
                                    date,
                                    shift
                            ))
                    .commit();
        }

    }

    private void initInstance() {
        toolbar = findViewById(R.id.toolbarFindWorkMateDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(
                    R.anim.from_top,
                    R.anim.to_bottom
            );
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
