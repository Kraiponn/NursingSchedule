package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.fragment.EditingFragment;
import com.ksntechnology.nursingschedule.fragment.ViewNursingDetailFragment;
import com.ksntechnology.nursingschedule.fragment.ViewNursingFragment;

public class ViewScheduleActivity extends AppCompatActivity
        implements EditingFragment.setOnEditItemClickListener,
                    EditingFragment.setOnReloadUpdateDataListener,
                    ViewNursingFragment.OnCallItemClickListener{

    private String mUserWorking = "";

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
            mUserWorking = userWorking;

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentMainViewNursing,
                            ViewNursingFragment.newInstance(userWorking))
                    .commit();
        }
    }

    private void replaceFragmentToActivity(int container, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(container, fragment)
                .commit();
    }

    private void initInstance() {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState == null) {
            outState.putString("user_working", mUserWorking);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mUserWorking = savedInstanceState.getString("user_working");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentViewSchedule_container,
                        ViewNursingFragment.newInstance(mUserWorking))
                .commit();*/
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
        FrameLayout ViewMoreInfo =
                findViewById(R.id.contentViewNursingDetail);

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
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentViewNursingDetail,
                            ViewNursingDetailFragment.newInstance(id))
                    .commit();*/
            replaceFragmentToActivity(
                    R.id.contentViewNursingDetail,
                    ViewNursingDetailFragment.newInstance(id)
            );
        }


    }

    @Override
    public void onCallEditOrDelete(int id) {
        FrameLayout ViewMoreInfo =
                findViewById(R.id.contentViewNursingDetail);

        if (ViewMoreInfo == null) {
            Intent itn = new Intent(
                    ViewScheduleActivity.this,
                    EditingActivity.class);
            itn.putExtra("id", id);
            startActivity(itn);

            overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
        } else {
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentViewNursingDetail,
                            EditingFragment.newInstance(id))
                    .commit();*/
            replaceFragmentToActivity(
                    R.id.contentViewNursingDetail,
                    EditingFragment.newInstance(id)
            );
        }
    }


    @Override
    public void onEditItemClicked() {
        /*Toast.makeText(this,
                "On Back Success", Toast.LENGTH_SHORT).show();*/
        onBackPressed();
    }

    @Override
    public void onReloadUpdateData() {
        FrameLayout isLandscapeLayout =
                findViewById(R.id.contentViewNursingDetail);

        if (isLandscapeLayout != null) {
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentMainViewNursing,
                            ViewNursingFragment.newInstance(mUserWorking))
                    .commit();*/
            replaceFragmentToActivity(
                    R.id.contentMainViewNursing,
                    ViewNursingFragment.newInstance(mUserWorking)
            );

            Fragment fmEdit = getSupportFragmentManager()
                    .findFragmentById(R.id.contentViewNursingDetail);

            getSupportFragmentManager().beginTransaction()
                    .remove(fmEdit)
                    .commit();
        } else {
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentMainViewNursing,
                            ViewNursingFragment.newInstance(mUserWorking))
                    .commit();*/
            replaceFragmentToActivity(
                    R.id.contentMainViewNursing,
                    ViewNursingFragment.newInstance(mUserWorking)
            );
        }
    }

}
