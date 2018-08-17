package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.fragment.ViewNursingDetailFragment;
import com.ksntechnology.nursingschedule.fragment.ViewScheduleRecordFragment;

public class ViewScheduleActivity extends AppCompatActivity
        implements ViewScheduleRecordFragment.onBackClickListener,
        ViewScheduleRecordFragment.onShowDetailListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //
        }

        initInstance();
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            String userWorking = intent.getStringExtra("user_working");

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentViewSchedule_container,
                            ViewScheduleRecordFragment.newInstance(userWorking))
                    .commit();
        }
    }

    private void initInstance() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(
                R.anim.from_top, R.anim.to_bottom
        );
    }

    @Override
    public void onBackClick() {
        onBackPressed();
    }


    @Override
    public void onShowDetail(int id) {
        /*Toast.makeText(getApplicationContext(),
                "ID No. " + id + " From fragment", Toast.LENGTH_SHORT).show();*/

        FrameLayout ViewMoreInfo = findViewById(R.id.contentViewDetailContainer_right);
        if (ViewMoreInfo == null) {
            Intent itn = new Intent(
                    ViewScheduleActivity.this,
                    ViewNursingDetailActivity.class);
            itn.putExtra("id", id);
            startActivity(itn);
            overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentViewDetailContainer_right,
                            ViewNursingDetailFragment.newInstance(id))
                    .commit();
        }


    }
}
