package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.fragment.WorkMateDetailFragment;
import com.ksntechnology.nursingschedule.fragment.WorkMateMainFragment;

public class FindWorkMateActivity extends AppCompatActivity
        implements WorkMateMainFragment.onCallWorkMateDetailListener{
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_work_mate);

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //
        }

        initInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentMain_FindWorkMate,
                            WorkMateMainFragment.newInstance())
                    .commit();
        }
    }

    private void initInstance() {
        toolbar = findViewById(R.id.toolbarFindWorkMate);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(
                R.anim.from_top,
                R.anim.to_bottom
        );
    }


    @Override
    public void setOnCallWorkMateDetail(String location, String shift, String date) {
        FrameLayout moreInfo = findViewById(R.id.contentDetail_FindWorkMate);

        if (moreInfo == null) {
            Intent intent = new Intent(
                    FindWorkMateActivity.this,
                    FindWorkMateDetailsActivity.class
            );
            intent.putExtra("location", location);
            intent.putExtra("date", date);
            intent.putExtra("shift", shift);
            startActivity(intent);
            overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentDetail_FindWorkMate,
                            WorkMateDetailFragment.newInstance(
                                    location, date, shift
                            ))
                    .commit();
        }
    }
}
