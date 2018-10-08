package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.fragment.WorkMateDetailFragment;

public class FindWorkMateDetailsActivity extends AppCompatActivity {

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
            String section = itn.getStringExtra("section");
            String section_sex = itn.getStringExtra("section_sex");

            //Log.d("ResponsesXYZ", location + " " + date);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer_FindWorkMateDetail,
                            WorkMateDetailFragment.newInstance(
                                    location,
                                    date,
                                    shift,
                                    section,
                                    section_sex
                            ))
                    .commit();
        }

    }

    private void initInstance() {
        //
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

}
