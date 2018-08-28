package com.ksntechnology.nursingschedule.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.dao.SignInRegisterResultDao;
import com.ksntechnology.nursingschedule.dialog.MyAlertDialog;
import com.ksntechnology.nursingschedule.manager.HttpNursingRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences mPreference;
    private EditText edtUserName;
    private EditText edtPswd;
    private CheckBox chkRemember;
    private Button btnLogIn;
    private TextView tvRegister;

    private final int TOAST_TYPE_SUCCESS = 1;
    private final int TOAST_TYPE_WARNING = 2;
    private final int TOAST_TYPE_ERROR = 3;

    /***********************************
     *  Method Zone
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //
        }

        initInstance();
    }


    private void initInstance() {
        edtUserName = findViewById(R.id.edit_userName);
        edtPswd = findViewById(R.id.edit_password);
        chkRemember = findViewById(R.id.checkbox_remember);
        btnLogIn = findViewById(R.id.button_login);
        tvRegister = findViewById(R.id.text_register);

        btnLogIn.setOnClickListener(btnLoginClickListener);
        chkRemember.setOnClickListener(checkRememberClick);
        tvRegister.setOnClickListener(tvRegisterClickListener);

        mPreference = getPreferences(Activity.MODE_PRIVATE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        readLastViewStatus();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
        );
        finish();
    }

    /*************************************
     *  Custom Method
     */

    private void readLastViewStatus() {
        boolean registerIsChecked =
                mPreference.getBoolean("login_remember", false);
        if (registerIsChecked) {
            edtUserName.setText(mPreference.getString("user_name", ""));
            edtPswd.setText(mPreference.getString("pswd", ""));
            chkRemember.setChecked(registerIsChecked);
        } else {
            SharedPreferences.Editor editor = mPreference.edit();
            editor.clear();
            editor.commit();
        }
    }

    private void putViewValueToPreferences() {
        String userName = edtUserName.getText().toString();
        String pswd = edtPswd.getText().toString();
        boolean remember = chkRemember.isChecked();

        SharedPreferences.Editor editor = mPreference.edit();
        if (chkRemember.isChecked()) {
            editor.putString("user_name", userName);
            editor.putString("pswd", pswd);
            editor.putBoolean("login_remember", remember);
            editor.apply();
        }else{
            editor.clear();
            editor.commit();
        }

    }

    private void toastMessage(String text, int alertType) {
        switch (alertType) {
            case 1:
                FancyToast.makeText(
                        this,
                        text,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        true
                ).show();
                break;
            case 2:
                FancyToast.makeText(
                        this,
                        text,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.WARNING,
                        true
                ).show();
                break;
            case 3:
                FancyToast.makeText(
                        this,
                        text,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR,
                        true
                ).show();
                break;
        }
    }

    private void setAlertEditView(String text, EditText edt) {
        new SimpleTooltip.Builder(this)
                .anchorView(edt)
                .text(text)
                .gravity(Gravity.BOTTOM)
                .animated(true)
                .transparentOverlay(false)
                .build()
                .show();
    }

    private void feedSignIn() {
        String userName = edtUserName.getText().toString().trim();
        String passWord = edtPswd.getText().toString().trim();
        String isRequest = "Please";

        if (userName.equals("")) {
            //edtUserName.setBackgroundResource(R.drawable.edit_warning_state);
            setAlertEditView(
                    "Please enter username",
                    edtUserName
            );
            return;
        } else if (passWord.equals("")) {
            //edtPswd.setBackgroundResource(R.drawable.edit_warning_state);
            setAlertEditView(
                    "Please enter password",
                    edtPswd
            );
            return;
        }

        Call<SignInRegisterResultDao> call =
                HttpNursingRequest.getInstance().getApi().requestSignIn(
                isRequest, userName, passWord
        );

        call.enqueue(new Callback<SignInRegisterResultDao>() {
            @Override
            public void onResponse(Call<SignInRegisterResultDao> call,
                                   Response<SignInRegisterResultDao> response) {
                if (response.isSuccessful()) {
                    String result = response.body().getErrMsg();
                    if (result.equals("SignIn Successfully")) {
                        toastMessage(
                                "Login successfully",
                                TOAST_TYPE_SUCCESS
                        );
                        String user_name = response.body().getUserName();
                        String email = response.body().getEmail();

                        Intent intent = new Intent(
                                LoginActivity.this,
                                MainControlActivity.class
                        );
                        intent.putExtra("user_name", user_name);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        overridePendingTransition(
                                R.anim.slide_in_right,
                                R.anim.slide_out_left
                        );
                    } else {
                        toastMessage(
                                "Invalid user name or password",
                                TOAST_TYPE_WARNING
                        );
                    }

                } else {
                    toastMessage(
                            "Invalid login : " +
                                    response.errorBody().toString(),
                            TOAST_TYPE_ERROR
                    );
                }
            }

            @Override
            public void onFailure(Call<SignInRegisterResultDao> call,
                                  Throwable t) {
                /*toastMessage(
                        "Login error " + t.getMessage(),
                        TOAST_TYPE_ERROR
                );*/
                showAlertDialog(
                        "TimeOut Or Throwable!",
                        "TimeOut: throwable at " +
                                t.getMessage(),
                        true
                );
            }
        });
    }

    private void showAlertDialog(String title, String msg, boolean alertType) {
        MyAlertDialog dialog = MyAlertDialog.newInstance(
                title, msg, alertType
        );

        dialog.show(getSupportFragmentManager(), null);
    }


    /******************************
     *  Listener Zone
     */
    View.OnClickListener btnLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            feedSignIn();
        }
    };


    View.OnClickListener checkRememberClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            putViewValueToPreferences();
        }
    };

    View.OnClickListener tvRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(
                    LoginActivity.this,
                    RegisterActivity.class
            ));
            overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
        }
    };


}
