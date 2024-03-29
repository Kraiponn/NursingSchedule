package com.ksntechnology.nursingschedule.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.ksntechnology.nursingschedule.R;
import com.ksntechnology.nursingschedule.dao.SignInRegisterResultDao;
import com.ksntechnology.nursingschedule.manager.HttpNursingRequest;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtUserName;
    private EditText edtPswd;
    private RadioButton radMale;
    private RadioButton radFeMale;
    private Button btnRegister;
    private Button btnBack;

    private final int TOAST_TYPE_SUCCESS = 1;
    private final int TOAST_TYPE_WARNING = 2;
    private final int TOAST_TYPE_ERROR = 3;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                                                "[a-zA-Z0-9_+&*-]+)*@" +
                                                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                                                "A-Z]{2,7}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //
        }

        initInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
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


    /**********************************
     *  Custom Method
     */
    private void initInstance() {
        edtEmail = findViewById(R.id.edit_email);
        edtUserName = findViewById(R.id.edit_userName);
        edtPswd = findViewById(R.id.edit_password);
        radMale = findViewById(R.id.radMale);
        radFeMale = findViewById(R.id.radFeMale);
        btnRegister = findViewById(R.id.button_register);
        btnBack = findViewById(R.id.buttonRegisterBack);

        btnRegister.setOnClickListener(btnRegisterClickListener);
        btnBack.setOnClickListener(btnBackClicked);
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

    private void register() {
        String email = edtEmail.getText().toString().trim();
        String userName = edtUserName.getText().toString().trim();
        String password = edtPswd.getText().toString().trim();
        String gender = "";

        if (email.equals("")) {
            setAlertEditView(edtEmail, "กรุณาใส่ Email ในการลงทะเบียน");
            return;
        }else if (!emailValidator(email)) {
            setAlertEditView(edtEmail, "กรุณาใส่รูปแบบ Email ไม่ถูกต้อง");
            return;
        }else if (userName.equals("")) {
            setAlertEditView(edtUserName, "กรุณาใส่ชื่อในการลงทะเบียน");
            return;
        }else if (password.equals("")) {
            setAlertEditView(edtPswd, "กรุณาใส่ระหัสผ่านในการลงทะเบียน");
            return;
        }

        if (!radMale.isChecked() && !radFeMale.isChecked()) {
            toastMessage(
                    "กรุณาเลือกเพศในการลงทะเบียน",
                    TOAST_TYPE_WARNING
            );
            return;
        } else {
            if (radMale.isChecked()) {
                gender = "Male";
            } else {
                gender = "Female";
            }
        }


        addNewUserToTable(userName, password, email, gender);
    }

    private void addNewUserToTable(String userName, String password,
                                   String email, String gender) {
        Call<SignInRegisterResultDao> call =
                HttpNursingRequest.getInstance().getApi().requestRegister(
                "IS_REQUEST", userName, password, email, gender, ""
        );

        call.enqueue(new Callback<SignInRegisterResultDao>() {
            @Override
            public void onResponse(Call<SignInRegisterResultDao> call,
                                   Response<SignInRegisterResultDao> response) {
                if (response.isSuccessful()) {
                    String result = response.body().getErrMsg();
                    if (result.equals("Register Successfully")) {
                        toastMessage(
                                "การสมัครลงทะเบียนสำเร็จ",
                                TOAST_TYPE_SUCCESS
                        );
                        overridePendingTransition(
                                R.anim.slide_in_left,
                                R.anim.slide_out_right
                        );
                        finish();
                    } else {
                        toastMessage(
                                response.body().getErrMsg(),
                                TOAST_TYPE_ERROR
                        );
                    }
                } else {
                    toastMessage(
                            "Invalid register : "
                                    + response.errorBody().toString(),
                            TOAST_TYPE_ERROR
                    );
                }
            }

            @Override
            public void onFailure(Call<SignInRegisterResultDao> call,
                                  Throwable t) {
                toastMessage(
                        "Invalid register : " + t.getMessage(),
                        TOAST_TYPE_ERROR
                );
            }
        });
    }


    private void setAlertEditView(EditText editText, String textMsg) {
        new SimpleTooltip.Builder(this)
                .anchorView(editText)
                .text(textMsg)
                .gravity(Gravity.BOTTOM)
                .animated(true)
                .transparentOverlay(false)
                .build()
                .show();
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;

        try {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
            return matcher.matches();
        } catch (Exception ex) {
            return false;
        }

    }


    /********************************
     *  Listener Zone
     */
    View.OnClickListener btnRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            register();
        }
    };

    View.OnClickListener btnBackClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

}
