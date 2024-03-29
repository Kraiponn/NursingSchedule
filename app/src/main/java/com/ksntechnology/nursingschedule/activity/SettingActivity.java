package com.ksntechnology.nursingschedule.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.fragment.SettingFragment;

public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //
        }
        //SettingFragment fragment = new SettingFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(
                        android.R.id.content,
                        SettingFragment.newInstance()
                )
                .commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.from_top,
                R.anim.to_bottom
        );
        finish();
    }
}
