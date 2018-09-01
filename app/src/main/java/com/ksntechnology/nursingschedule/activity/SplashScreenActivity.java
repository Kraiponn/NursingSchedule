package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksntechnology.nursingschedule.R;

public class SplashScreenActivity extends AppCompatActivity {
    private Handler mHandler;
    private Runnable mRunnable;
    //private MediaPlayer mPlayer;
    private TextView tvTitle;
    //private ImageView imgLogo;
    private long delay_time = 0L;
    private long time = 4000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //imgLogo = findViewById(R.id.image_logo);
        tvTitle = findViewById(R.id.text_title);

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(
                        SplashScreenActivity.this,
                        LoginActivity.class
                );
                startActivity(intent);
                finish();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        delay_time = time;
        mHandler.postDelayed(mRunnable, delay_time);
        time = System.currentTimeMillis();

        startAnimate();
    }

    private void startAnimate() {
        tvTitle.animate().rotationY(360).setDuration(4000).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        time = delay_time - (System.currentTimeMillis() - time);
    }

}
