package com.ksntechnology.nursingschedule.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.dialog.ConfirmDialog;
import com.ksntechnology.nursingschedule.fragment.MainControlFragment;
import com.ksntechnology.nursingschedule.fragment.MainNavigationActionMenuFragment;

public class MainControlActivity extends AppCompatActivity
        implements MainControlFragment.setOnItemClickListener,
        MainNavigationActionMenuFragment.setOnNavigationMenuItemClickListener{
    private String mUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_control);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //
        }

        initInstance();
        if (savedInstanceState == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainContentContainer,
                            MainControlFragment.newInstance(0))
                    .commit();
        }
    }

    private void initInstance() {
        initUserLogin();

    }


    private void initUserLogin() {
        Intent intent = getIntent();
        String userName = intent.getStringExtra("user_name");

        mUser = userName;
    }

    private void showConfirmExitDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(
                R.layout.fragment_custom_confirm_exit_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(view);

        TextView tvTitle = view.findViewById(R.id.textTitle);
        Button btnConfirm = view.findViewById(R.id.buttonConfirm);
        Button btnCancel = view.findViewById(R.id.buttonCancel);

        tvTitle.setText("คุณต้องการออกจาก\n Nursing Schedule?");
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                );
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showConfirmExitDialog();
    }

    @Override
    public void onItemClicked(View view) {
        switch (view.getId()) {
            case R.id.image_action_menu_setting: {
                Fragment fm = getSupportFragmentManager()
                        .findFragmentById(R.id.mainContentContainer);
                if (fm instanceof MainNavigationActionMenuFragment == false) {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(
                                    R.anim.slide_from_right, R.anim.slide_to_left,
                                    R.anim.slide_from_left, R.anim.slide_to_right
                            )
                            .add(R.id.mainContentContainer,
                                    MainNavigationActionMenuFragment.newInstance(0))
                            .addToBackStack(null)
                            .commit();
                }
                break;
            }
            case R.id.button_viewData: {
                Intent intent = new Intent(
                        MainControlActivity.this,
                        ViewScheduleActivity.class
                );
                intent.putExtra("user_working", mUser);
                startActivity(intent);

                overridePendingTransition(
                        R.anim.from_bottom, R.anim.to_top
                );
                break;
            }
            case R.id.button_AddItem: {
                Intent intent = new Intent(
                        MainControlActivity.this,
                        AddItemActivity.class
                );
                intent.putExtra("user_working", mUser);
                startActivity(intent);

                overridePendingTransition(
                        R.anim.from_bottom, R.anim.to_top
                );
                break;
            }
            case R.id.button_viewFriends: {
                Intent intent = new Intent(
                        MainControlActivity.this,
                        FindWorkMateActivity.class
                );
                intent.putExtra("user_working", mUser);
                startActivity(intent);

                overridePendingTransition(
                        R.anim.from_bottom, R.anim.to_top
                );
                break;
            }
            case R.id.button_viewStatistic: {
                break;
            }
        }
    }

    @Override
    public void onNavigationMenuItemClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutEXit: {
                break;
            }
            case R.id.layoutManual:{
                break;
            }
            case R.id.layoutSetting: {
                startActivity(new Intent(
                        MainControlActivity.this,
                        SettingActivity.class
                ));
                overridePendingTransition(
                        R.anim.from_bottom, R.anim.to_top
                );
                break;
            }
        }
    }


}
