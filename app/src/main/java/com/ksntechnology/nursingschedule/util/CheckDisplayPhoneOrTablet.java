package com.ksntechnology.nursingschedule.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import com.ksntechnology.nursingschedule.R;

public class CheckDisplayPhoneOrTablet extends Activity {

    public void setScreenOrientationPhoneOrTable() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //
        }
    }

}
