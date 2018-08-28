package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.fragment.ViewNursingDetailFragment;
import com.ksntechnology.nursingschedule.fragment.ViewScheduleRecordFragment;

public class ViewNursingDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nursing_detail);

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //
        }

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            int id = intent.getIntExtra("id", 0);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentViewDetailContainer,
                            ViewNursingDetailFragment.newInstance(id))
                    .commit();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(
                R.anim.from_top, R.anim.to_bottom
        );
    }
}
